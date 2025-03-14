import LoginPage from "../component/auth/LoginPage";
import ForgotPage from "../component/auth/ForgotPage";
import { Navigate } from 'react-router-dom';
import CallbackPage from "../component/auth/CallbackPage";
import Home from "../component/Home";

const routes = [
    {
        path: '/', // 当访问根路径时，重定向到 /login
        element: <Navigate to="/login" replace />,
        auth:false
    },
    {
        path: '/login', // LoginPage 的新路径
        element: <LoginPage />,
        auth:false
    },
    {
        path: '/forgot',
        element: <ForgotPage />,
        auth:false
    },
    {
        path: '/callback',
        element: <CallbackPage />,
        auth:false
    },
    {
        path: '/home',
        element: <Home/>,
        auth:true
    }
];

export default routes;
