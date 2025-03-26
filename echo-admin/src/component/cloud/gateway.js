import React, { useEffect, useState } from 'react';
import { getAllGateway } from "../../axios/api/gateway";
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Tooltip } from '@mui/material';

const GateWay = () => {
    const [routes, setRoutes] = useState([]);

    useEffect(() => {
        Gateway();
    }, []);

    const Gateway = async () => {
        try {
            const response = await getAllGateway();
            setRoutes(response.data || []);
            console.log(response);
        } catch (error) {
            setRoutes([]);
        }
    };

    return (
        <TableContainer component={Paper}>
            <Table>
                <TableHead>
                    <TableRow>
                        <TableCell align="center">
                            <Tooltip title="路由的唯一标识符" placement="top">
                                <span>ID</span>
                            </Tooltip>
                        </TableCell>
                        <TableCell align="center">
                            <Tooltip title="路由的断言条件" placement="top">
                                <span>断言条件</span>
                            </Tooltip>
                        </TableCell>
                        <TableCell align="center">
                            <Tooltip title="路由的过滤条件" placement="top">
                                <span>过滤条件</span>
                            </Tooltip>
                        </TableCell>
                        <TableCell align="center">
                            <Tooltip title="路由的目标URI" placement="top">
                                <span>目标URI</span>
                            </Tooltip>
                        </TableCell>
                        <TableCell align="center">
                            <Tooltip title="路由的元数据" placement="top">
                                <span>元数据</span>
                            </Tooltip>
                        </TableCell>
                        <TableCell align="center">
                            <Tooltip title="路由的优先级顺序" placement="top">
                                <span>优先级</span>
                            </Tooltip>
                        </TableCell>
                        <TableCell align="center">
                            <Tooltip title="路由是否启用" placement="top">
                                <span>是否启用</span>
                            </Tooltip>
                        </TableCell>
                    </TableRow>
                </TableHead>
                <TableBody>
                    {routes.map((route) => (
                        <TableRow key={route.id}>
                            <TableCell>{route.id}</TableCell>
                            <TableCell>
                                <ul>
                                    {(route.predicates || []).map((predicate, index) => (
                                        <li key={index}>{predicate.name} [{Object.values(predicate.args).join(', ')}]</li>
                                    ))}
                                </ul>
                            </TableCell>
                            <TableCell>
                                <ul>
                                    {(route.filters || []).map((filter, index) => (
                                        <li key={index}>{filter.name} [{Object.values(filter.args).join(', ')}]</li>
                                    ))}
                                </ul>
                            </TableCell>
                            <TableCell>{route.uri}</TableCell>
                            <TableCell>{JSON.stringify(route.metadata)}</TableCell>
                            <TableCell>{route.order}</TableCell>
                            <TableCell>{route.enabled ? 'True' : 'False'}</TableCell>
                        </TableRow>
                    ))}
                </TableBody>
            </Table>
        </TableContainer>
    );
};

export default GateWay;
