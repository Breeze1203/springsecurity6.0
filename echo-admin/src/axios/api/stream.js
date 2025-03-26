import {get} from "../axios";

export const getAllStream = () => {
    return get('/stream/all'); // 获取系统信息，包含时间和用户信息
};
