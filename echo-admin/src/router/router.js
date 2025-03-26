import Login from "../component/auth/login";
import Forget from "../component/auth/forget";
import { Navigate } from 'react-router-dom';
import Callback from "../component/auth/callback";
import Home from "../component/desbord/home";

const routes = [
    {
        path: '/', // 当访问根路径时，重定向到 /login
        element: <Navigate to="/login" replace />,
        auth:false
    },
    {
        path: '/login', // Login 的新路径
        element: <Login />,
        auth:false
    },
    {
        path: '/forgot',
        element: <Forget />,
        auth:false
    },
    {
        path: '/callback',
        element: <Callback />,
        auth:false
    },
    {
        path: '/home',
        element: <Home/>,
        auth:true
    }
];

export default routes;
