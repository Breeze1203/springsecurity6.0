import React from 'react';
import {Navigate } from 'react-router-dom';

// 检查用户是否已登录
const isAuthenticated = () => {
    // 返回 true 表示已登录，false 表示未登录
    return localStorage.getItem("username") !== null;
};

const Private = ({ children }) => {
    // 如果已登录，渲染子组件；未登录，跳转到 login
    return isAuthenticated() ? children : <Navigate to="/login" replace />;
};
export default Private;
