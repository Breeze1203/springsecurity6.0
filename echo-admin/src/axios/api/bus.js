import {get} from "../axios";

export const getBusConfig = (path) => {
    return get(path);
};

export const initBusConfig = async (env) => {
    const path = env === "prod" ? "/bus-c/getConfig" : "/bus-b/getConfig";
    try {
        const resp = await getBusConfig(path);
        // 确保 propertySources 存在且不为空
        if (resp) {
            console.log(resp.data);
            return resp;
        }
        return {};
    } catch (error) {
        console.log()
    }
};
