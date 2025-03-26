import { get } from '../axios';

export const getAllRegistries = () => {
    return get('/instances/all'); // 获取系统信息，包含时间和用户信息
};

export const stopProvider = (server) => {
    return get(`/instances/stop/${server}`)
}

export const restartProvider = (server) => {
    return get(`/instances/restart/${server}`)
}

export const getProviderStatus=()=>{
    return get('/instances/provider-round/getStatus')
}
