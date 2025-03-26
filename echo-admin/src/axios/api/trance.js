import axios from "axios";
export const getTrance = (limit) => {
    return  axios.get('http://localhost:9411/api/v2/traces?limit='+limit, {
        headers: {
            Accept: 'application/json',
        },
    });
};
