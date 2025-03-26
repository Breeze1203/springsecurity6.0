import {get} from "../axios";

export const getLoadbanlance = (strategy) => {
    return get(`/loadbanlance/getProvider/${strategy}`); // 获取系统信息，包含时间和用户信息
};
