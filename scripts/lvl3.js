import http from 'k6/http';
import { check } from 'k6';

export const options = {
    scenarios: {
        moderate_load: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '15s', target: 200 },
                { duration: '30s', target: 500 },
                { duration: '2m', target: 500 },
                { duration: '15s', target: 0 },
            ],
        },
    },
    thresholds: {
        http_req_duration: ['p(95)<250', 'p(99)<500'],
    },
};

const url = 'http://localhost:8080/api/transactions';
const params = {
    headers: {
        'Content-Type': 'application/json',
        'Connection': 'keep-alive',
    },
};

export default function () {
    const payload = JSON.stringify({
        accountId: `ACC-${__VU}-${__ITER}`,
        amount: 500.00,
        operationType: 'DEBIT'
    });

    const response = http.post(url, payload, params);

    check(response, {
        'status is 201': (r) => r.status === 201,
    });
}