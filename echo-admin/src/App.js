import {BrowserRouter, Routes, Route} from 'react-router-dom';
import routes from "./router/router";
import Private from "./component/auth/private";

function renderRoutes(routeList) {
    return routeList.map((route, index) => {
        // 根据路由配置决定是否需要包裹 Private
        const element = route.auth ? (
            <Private>{route.element}</Private>
        ) : (
            route.element
        );
        console.log(`Route: ${route.path}, auth: ${route.auth}, wrapped: ${route.auth ? 'Private' : 'Direct'}`);

        return (
            <Route key={index} path={route.path} element={element}>
                {route.children && renderRoutes(route.children)}
            </Route>
        );
    })
}

function App() {
    return (
        <BrowserRouter basename="/echo-admin">
            <Routes>{renderRoutes(routes)}</Routes>
        </BrowserRouter>
    );
}

export default App;
