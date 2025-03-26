import {get} from "../axios";

export const getSlow = () => {
    return get('/break/getSlow'); // 获取系统信息，包含时间和用户信息
};

export const getSlowTwo = () => {
    return get('/break/getSlowTwo'); // 获取系统信息，包含时间和用户信息
};

