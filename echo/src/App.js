import {BrowserRouter, Routes, Route} from 'react-router-dom';
import routes from "./router/router";
import PrivateRoute from "./component/auth/PrivateRoute";

function renderRoutes(routeList) {
    return routeList.map((route, index) => {
        // 根据路由配置决定是否需要包裹 PrivateRoute
        const element = route.auth ? (
            <PrivateRoute>{route.element}</PrivateRoute>
        ) : (
            route.element
        );
        console.log(`Route: ${route.path}, auth: ${route.auth}, wrapped: ${route.auth ? 'PrivateRoute' : 'Direct'}`);

        return (
            <Route key={index} path={route.path} element={element}>
                {route.children && renderRoutes(route.children)}
            </Route>
        );
    })
}

function App() {
    return (
        <BrowserRouter>
            <Routes>{renderRoutes(routes)}</Routes>
        </BrowserRouter>
    );
}

export default App;
