/**
 * 设置 Cookie
 * @param {string} name - Cookie 名称
 * @param {string} value - Cookie 值
 * @param {number} days - 过期时间（天），默认 7 天
 * @param {string} path - Cookie 路径，默认 '/'
 * @param {string} domain - Cookie 域名，默认空
 * @param {boolean} secure - 是否仅通过 HTTPS 传输，默认 false
 */
export const setCookie = (
    name,
    value,
    days = 7,
    path = '/',
    domain = '',
    secure = false
) => {
    let expires = '';
    if (days) {
        const date = new Date();
        date.setTime(date.getTime() + days * 24 * 60 * 60 * 1000);
        expires = `; expires=${date.toUTCString()}`;
    }
    const c = `${name}=${encodeURIComponent(value)}${expires}; path=${path}${
        domain ? `; domain=${domain}` : ''
    }${secure ? '; Secure' : ''}`;
    document.cookie = c;
};

/**
 * 获取 Cookie
 * @param {string} name - Cookie 名称
 * @returns {string|null} - Cookie 值，如果不存在则返回 null
 */
export const getCookie = (name) => {
    const nameEQ = `${name}=`;
    const cookies = document.cookie.split(';');
    for (let cookie of cookies) {
        cookie = cookie.trim();
        if (cookie.indexOf(nameEQ) === 0) {
            return decodeURIComponent(cookie.substring(nameEQ.length));
        }
    }
    return null;
};

/**
 * 删除 Cookie
 * @param {string} name - Cookie 名称
 * @param {string} path - Cookie 路径，默认 '/'
 * @param {string} domain - Cookie 域名，默认空
 */
export const deleteCookie = (name, path = '/', domain = '') => {
    setCookie(name, '', -1, path, domain);
};

/**
 * 检查 Cookie 是否存在
 * @param {string} name - Cookie 名称
 * @returns {boolean} - 是否存在
 */
export const hasCookie = (name) => {
    return getCookie(name) !== null;
};

/**
 * 获取所有 Cookie
 * @returns {Object} - 键值对形式的所有 Cookie
 */
export const getAllCookies = () => {
    const cookies = {};
    const allCookies = document.cookie.split(';');
    for (let cookie of allCookies) {
        cookie = cookie.trim();
        const [name, value] = cookie.split('=');
        if (name) {
            cookies[name] = decodeURIComponent(value);
        }
    }
    return cookies;
};



