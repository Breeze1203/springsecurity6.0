import { get } from '../axios';

export const getAllGateway = () => {
    return get('/gateway/all'); // 获取系统信息，包含时间和用户信息
};
