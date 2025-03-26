import React, { useState } from 'react';
import {
    Container,
    Typography,
    Button,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Box,
    TextField,
    CircularProgress,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import {getLoadbanlance} from "../../axios/api/loadbanlance";

// 自定义表格样式
const StyledTableCell = styled(TableCell)(({ theme }) => ({
    '&.MuiTableCell-head': {
        backgroundColor: '#ffffff',
        color: theme.palette.grey[900],
        fontWeight: 'bold',
        whiteSpace: 'nowrap',
        padding: '12px 16px',
        borderBottom: `2px solid ${theme.palette.grey[400]}`,
    },
    '&.MuiTableCell-body': {
        padding: '10px 16px',
        borderBottom: `1px solid ${theme.palette.grey[300]}`,
        whiteSpace: 'nowrap',
    },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:hover': {
        backgroundColor: theme.palette.grey[100],
    },
}));

const LoadBalancer = () => {
    const [results, setResults] = useState([]); // 存储请求结果
    const [loading, setLoading] = useState(false); // 加载状态
    const [requestCount, setRequestCount] = useState(5); // 默认发送 5 次请求

    // 发送单个请求
    const sendRequest = async (strategy) => {
        setLoading(true);
        try {
            const response = await getLoadbanlance(strategy);
            setResults((prev) => [
                ...prev,
                { id: Date.now(), strategy, port: response.data },
            ]);
        } catch (error) {
            setResults((prev) => [
                ...prev,
                { id: Date.now(), strategy, port: '获取数据失败' },
            ]);
        } finally {
            setLoading(false);
        }
    };

    // 发送多次请求
    const sendMultipleRequests = async (strategy) => {
        setLoading(true);
        try {
            const requests = Array.from({ length: requestCount }, () =>
                getLoadbanlance(strategy)
            );
            const responses = await Promise.all(requests);
            const newResults = responses.map((response, index) => ({
                id: Date.now() + index,
                strategy,
                port: response.data,
            }));
            setResults((prev) => [...prev, ...newResults]);
        } catch (error) {
            setResults((prev) => [
                ...prev,
                { id: Date.now(), strategy, port: '获取数据失败' },
            ]);
        } finally {
            setLoading(false);
        }
    };

    // 清空结果
    const clearResults = () => {
        setResults([]);
    };

    // 表头
    const headers = ['请求ID', '负载均衡策略', '目标端口'];

    return (
        <Container maxWidth="lg" sx={{ mt: 4, mb: 4 }}>


            <Box sx={{ display: 'flex', gap: 2, mb: 3, alignItems: 'center' }}>
                <Button
                    variant="contained"
                    onClick={() => sendRequest('round')}
                    disabled={loading}
                >
                    单次轮询请求
                </Button>
                <Button
                    variant="contained"
                    onClick={() => sendRequest('random')}
                    disabled={loading}
                >
                    单次随机请求
                </Button>
                <TextField
                    label="请求次数"
                    type="number"
                    value={requestCount}
                    onChange={(e) => setRequestCount(Math.max(1, parseInt(e.target.value, 10)))}
                    size="small"
                    sx={{ width: 120 }}
                />
                <Button
                    variant="outlined"
                    onClick={() => sendMultipleRequests('round')}
                    disabled={loading}
                >
                    发送多次轮询请求
                </Button>
                <Button
                    variant="outlined"
                    onClick={() => sendMultipleRequests('random')}
                    disabled={loading}
                >
                    发送多次随机请求
                </Button>
                <Button variant="outlined" color="error" onClick={clearResults} disabled={loading}>
                    清空结果
                </Button>
            </Box>

            {loading && (
                <Box sx={{ display: 'flex', justifyContent: 'center', my: 4 }}>
                    <CircularProgress />
                </Box>
            )}

            {!loading && (
                <TableContainer component={Paper} sx={{ boxShadow: 3, borderRadius: 2 }}>
                    <Table sx={{ minWidth: 650 }}>
                        <TableHead>
                            <TableRow>
                                {headers.map((header) => (
                                    <StyledTableCell key={header}>{header}</StyledTableCell>
                                ))}
                            </TableRow>
                        </TableHead>
                        <TableBody>
                            {results.length === 0 && (
                                <StyledTableRow>
                                    <StyledTableCell colSpan={3} align="center">
                                        暂无请求记录
                                    </StyledTableCell>
                                </StyledTableRow>
                            )}
                            {results.map((result) => (
                                <StyledTableRow key={result.id}>
                                    <StyledTableCell>{result.id}</StyledTableCell>
                                    <StyledTableCell>{result.strategy}</StyledTableCell>
                                    <StyledTableCell>{result.port}</StyledTableCell>
                                </StyledTableRow>
                            ))}
                        </TableBody>
                    </Table>
                </TableContainer>
            )}
        </Container>
    );
};

export default LoadBalancer;
