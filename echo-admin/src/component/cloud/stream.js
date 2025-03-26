import React, { useEffect, useState } from 'react';
import { Table, TableBody, TableCell, TableContainer, TableHead, TableRow, Paper, Tooltip, Box } from '@mui/material';
import { getAllStream } from "../../axios/api/stream";

// 获取 Stream 数据的函数
const fetchStreamData = async () => {
    try {
        const response = await getAllStream();
        return response || { data: {} };
    } catch (error) {
        console.error('Failed to fetch stream data:', error);
        return { data: {} };
    }
};

// 展平 BindingProperties 对象
const flattenBinding = (bindingName, binding) => {
    const { consumer = {}, producer = {}, ...rest } = binding;
    return {
        bindingName,
        bindingType: bindingName.includes('-in-') ? 'input' : 'output',
        destination: rest.destination || '-',
        group: rest.group || '-',
        contentType: rest.contentType || '-',
        binder: rest.binder || '-',
        ...Object.keys(consumer).reduce((acc, key) => ({
            ...acc,
            [`consumer.${key}`]: consumer[key] || '-'
        }), {}),
        ...Object.keys(producer).reduce((acc, key) => ({
            ...acc,
            [`producer.${key}`]: producer[key] || '-'
        }), {}),
    };
};

// 处理 Map 数据
const processBindings = (bindingsMap) => {
    return Object.entries(bindingsMap).map(([key, value]) => flattenBinding(key, value));
};

// 动态生成表头
const generateColumns = (bindings) => {
    if (bindings.length === 0) return [];
    const allKeys = new Set();
    bindings.forEach(binding => {
        Object.keys(binding).forEach(key => allKeys.add(key));
    });

    return Array.from(allKeys).map(key => ({
        field: key,
        label: key
            .replace('consumer.', '消费者 - ')
            .replace('producer.', '生产者 - ')
            .replace(/([A-Z])/g, ' $1')
            .trim()
            .replace(/^./, str => str.toUpperCase()),
        tooltip: `绑定属性: ${key}`
    }));
};

const Stream = () => {
    const [bindings, setBindings] = useState([]);
    const [columns, setColumns] = useState([]);

    useEffect(() => {
        const fetchStreams = async () => {
            try {
                const response = await fetchStreamData();
                const flattenedBindings = processBindings(response.data || {});
                setBindings(flattenedBindings);
                setColumns(generateColumns(flattenedBindings));
            } catch (error) {
                setBindings([]);
                setColumns([]);
            }
        };
        fetchStreams();
    }, []);

    // 格式化单元格值
    const formatCellValue = (value) => {
        if (value === null || value === undefined || value === '') {
            return '-';
        }
        if (typeof value === 'boolean') {
            return value ? '是' : '否';
        }
        if (Array.isArray(value)) {
            return value.length > 0 ? value.join(', ') : '-';
        }
        if (typeof value === 'object') {
            return JSON.stringify(value);
        }
        return value.toString();
    };

    return (
        <Box sx={{ height: '100%', width: '100%',backgroundColor: '#fff',display: 'flex', flexDirection: 'column' }}>
            <TableContainer
                component={Paper}
                sx={{
                    flex: 1, // 占满 Box 的剩余空间
                    maxHeight: '100%', // 限制高度为 Box 的高度
                    backgroundColor: '#fff',
                    overflow: 'auto' // 启用滚动
                }}
            >
                <Table stickyHeader>
                    <TableHead>
                        <TableRow>
                            {columns.map((column) => (
                                <TableCell
                                    key={column.field}
                                    align="center"
                                    sx={{
                                        whiteSpace: 'nowrap',
                                        backgroundColor: '#fff',
                                        zIndex: 2,
                                    }}
                                >
                                    <Tooltip title={column.tooltip} placement="top">
                                        <span>{column.label}</span>
                                    </Tooltip>
                                </TableCell>
                            ))}
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {bindings.length > 0 && (
                            <TableRow
                                sx={{
                                    position: 'sticky',
                                    top: 56,
                                    backgroundColor: '#fff',
                                    zIndex: 1,
                                }}
                            >
                                {columns.map((column) => (
                                    <TableCell
                                        key={column.field}
                                        align="center"
                                        sx={{ whiteSpace: 'nowrap' }}
                                    >
                                        {formatCellValue(bindings[0][column.field])}
                                    </TableCell>
                                ))}
                            </TableRow>
                        )}
                        {bindings.slice(1).map((binding) => (
                            <TableRow key={binding.bindingName}>
                                {columns.map((column) => (
                                    <TableCell
                                        key={column.field}
                                        align="center"
                                        sx={{ whiteSpace: 'nowrap' }}
                                    >
                                        {formatCellValue(binding[column.field])}
                                    </TableCell>
                                ))}
                            </TableRow>
                        ))}
                    </TableBody>
                </Table>
            </TableContainer>
        </Box>
    );
};

export default Stream;
