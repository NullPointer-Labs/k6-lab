import http from 'k6/http';
import { check } from 'k6';

export const options = {
    scenarios: {
        extreme_throughput: {
            executor: 'ramping-vus',
            startVUs: 0,
            stages: [
                { duration: '20s', target: 1000 },
                { duration: '30s', target: 3000 },
                { duration: '30s', target: 6000 },
                { duration: '2m', target: 6000 },
                { duration: '20s', target: 0 },
            ],
        },
    },
    thresholds: {
        http_req_failed: ['rate<0.05'],
        http_req_duration: ['p(95)<3000'],
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