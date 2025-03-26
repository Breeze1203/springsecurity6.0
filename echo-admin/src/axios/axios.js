import axios from 'axios';

// 创建 axios 实例
const instance = axios.create({
    baseURL: process.env.REACT_APP_API_URL || 'http://localhost:8086/api', // 设置基础 URL，可根据环境变量动态调整
    timeout: 15000, // 请求超时时间（单位：毫秒）
    headers: {
        'Content-Type': 'application/json', // 默认请求头
        'Accept': 'application/json',
    },
});

// 请求拦截器
instance.interceptors.request.use(
    (config) => {
        console.log(config.url)
        // 在发送请求前做些什么，例如添加 token
        const token = localStorage.getItem('token'); // 假设 token 存在 localStorage 中
        if (token) {
            config.headers.Authorization = `Bearer ${token}`; // 添加 Authorization 头
        }
        return config;
    },
    (error) => {
        // 请求错误处理
        return Promise.reject(error);
    }
);

// 响应拦截器
instance.interceptors.response.use(
    (response) => {
        // 对响应数据做些什么，例如直接返回 data
        return response.data;
    },
    (error) => {
        // 响应错误处理
        if (error.response) {
            // 服务器返回了错误状态码
            const {status} = error.response;
            if (status === 401) {
                // 未授权，可能是 token 过期
                console.error('Unauthorized, redirecting to login...');
                window.location.href = '/login';
            } else if (status === 404) {
                console.error('Resource not found');
            } else if (status >= 500) {
                console.error('Server error');
            }
        } else if (error.request) {
            // 请求发出但没有收到响应（例如网络问题）
            console.error('Network Error:', error.message);
        } else {
            // 其他错误
            console.error('Error:', error.message);
        }
        return Promise.reject(error);
    }
);

// 封装常用的请求方法（可选）
export const get = (url, params = {}) => {
    return instance.get(url, {params});
};

export const post = (url, data = {}) => {
    return instance.post(url, data);
};

export const put = (url, data = {}) => {
    return instance.put(url, data);
};

export const del = (url, params = {}) => {
    return instance.delete(url, {params});
};

// 默认导出 axios 实例
export default instance;
