import http from 'k6/http';
import { check, sleep } from 'k6';

export const options = {
    vus: 1,
    iterations: 1,
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
        accountId: `ACC-WARMUP`,
        amount: 500.00,
        operationType: 'DEBIT'
    });

    const response = http.post(url, payload, params);

    check(response, {
        'status is 201': (r) => r.status === 201,
    });

    console.log('Warmup complete. Idling for 60 seconds to collect baseline metrics...');
    sleep(60);
}