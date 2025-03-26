import React, {useState, useEffect} from 'react';
import {
    Box,
    Button,
    Card,
    CardContent,
    Typography,
    Grid,
    CircularProgress,
    Alert,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Chip,
} from '@mui/material';
import {
    CheckCircle as CheckCircleIcon,
    Cancel as CancelIcon,
} from '@mui/icons-material';
import {getSlow, getSlowTwo} from "../../axios/api/break";
import {restartProvider, stopProvider, getProviderStatus} from "../../axios/api/registry"; // 新增 getProviderStatus

const Break = () => {
    const [slowResult, setSlowResult] = useState('');
    const [slowTwoResult, setSlowTwoResult] = useState('');
    const [loadingSlow, setLoadingSlow] = useState(false);
    const [loadingSlowTwo, setLoadingSlowTwo] = useState(false);
    const [providerStatus, setProviderStatus] = useState(''); // 初始为空，等待后端数据
    const [error, setError] = useState('');

    // 组件挂载时获取 provider 状态
    useEffect(() => {
        const fetchProviderStatus = async () => {
            try {
                const response = await getProviderStatus();
                setProviderStatus(response.data); // 根据实际返回格式调整
            } catch (err) {
                setError('获取 Provider Round状态失败: ' + (err.response?.data || err.message));
            }
        };
        fetchProviderStatus();
    }, []); // 空依赖数组，仅在挂载时运行

    const renderStatus = (status) => {
        if (!status) return '加载中...'; // 未获取到状态时显示加载中
        return (
            <Chip
                icon={status === 'UP' ? <CheckCircleIcon/> : <CancelIcon/>}
                label={status}
                color={status === 'UP' ? 'success' : 'error'}
                size="small"
                variant="outlined"
            />
        );
    };

    // 请求 /getSlow
    const fetchSlow = async () => {
        setLoadingSlow(true);
        setError('');
        try {
            const response = await getSlow();
            setSlowResult(response || '无数据');
        } catch (err) {
            setSlowResult('请求失败');
            setError('请求 /getSlow 失败: ' + (err.response?.data || err.message));
        } finally {
            setLoadingSlow(false);
        }
    };

    // 请求 /getSlowTwo
    const fetchSlowTwo = async () => {
        setLoadingSlowTwo(true);
        setError('');
        try {
            const response = await getSlowTwo();
            setSlowTwoResult(response || '无数据');
        } catch (err) {
            setSlowTwoResult('请求失败');
            setError('请求 /getSlowTwo 失败: ' + (err.response?.data || err.message));
        } finally {
            setLoadingSlowTwo(false);
        }
    };

    // 启动 Provider
    const startProvider = async () => {
        setError('');
        try {
            const response = await restartProvider("provider-round");
            setProviderStatus(response.msg); // 更新状态
        } catch (err) {
            setError('启动 Provider Round 失败: ' + (err.response?.data || err.message));
        }
    };

    // 停止 Provider
    const stop = async () => {
        setError('');
        try {
            const data = await stopProvider("provider-round");
            setProviderStatus(data.msg); // 更新状态
        } catch (err) {
            setError('停止 Provider 失败: ' + (err.response?.data || err.message));
        }
    };

    return (
        <Box sx={{p: 3, height: '100%', display: 'flex', flexDirection: 'column'}}>
            <Card sx={{mb: 3}}>
                <CardContent>
                    <Typography variant="body2" color="text.secondary" gutterBottom>
                        测试断路器效果，启动provider-round返回端口是8082或2222，反之8084（注意:有两个服务提供者，注册中心同步有一定延迟）
                    </Typography>
                    <TableContainer component={Paper}>
                        <Table>
                            <TableHead>
                                <TableRow>
                                    <TableCell>状态</TableCell>
                                    <TableCell>服务ID</TableCell>
                                    <TableCell>操作</TableCell>
                                </TableRow>
                            </TableHead>
                            <TableBody>
                                <TableRow>
                                    <TableCell>{renderStatus(providerStatus)}</TableCell>
                                    <TableCell>provider-round</TableCell>
                                    <TableCell>
                                        <Grid container spacing={1}>
                                            <Grid item>
                                                <Button
                                                    variant="contained"
                                                    color="success"
                                                    size="small"
                                                    onClick={startProvider}
                                                    disabled={providerStatus === 'UP' || providerStatus === 'UNKNOWN'}
                                                >
                                                    启动
                                                </Button>
                                            </Grid>
                                            <Grid item>
                                                <Button
                                                    variant="contained"
                                                    color="error"
                                                    size="small"
                                                    onClick={stop}
                                                    disabled={providerStatus === 'DOWN' || providerStatus === 'UNKNOWN'}
                                                >
                                                    停止
                                                </Button>
                                            </Grid>
                                        </Grid>
                                    </TableCell>
                                </TableRow>
                            </TableBody>
                        </Table>
                    </TableContainer>
                </CardContent>
            </Card>

            {/* 请求测试区域 */}
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <Card>
                        <CardContent>
                            <Typography variant="h6">请求 /getSlow</Typography>
                            <Button
                                variant="contained"
                                onClick={fetchSlow}
                                disabled={loadingSlow}
                                sx={{mt: 2}}
                            >
                                {loadingSlow ? <CircularProgress size={24}/> : '发送请求'}
                            </Button>
                            <Box sx={{mt: 2}}>
                                <Typography variant="subtitle1">响应结果:</Typography>
                                <Alert
                                    severity={slowResult.code === 200 ? 'success' : 'error'}
                                    sx={{mt: 1}}
                                >
                                    {slowResult ? JSON.stringify(slowResult, null, 2) : '未请求'}
                                </Alert>
                            </Box>
                        </CardContent>
                    </Card>
                </Grid>
                <Grid item xs={12} md={6}>
                    <Card>
                        <CardContent>
                            <Typography variant="h6">请求 /getSlowTwo</Typography>
                            <Button
                                variant="contained"
                                onClick={fetchSlowTwo}
                                disabled={loadingSlowTwo}
                                sx={{mt: 2}}
                            >
                                {loadingSlowTwo ? <CircularProgress size={24}/> : '发送请求'}
                            </Button>
                            <Box sx={{mt: 2}}>
                                <Typography variant="subtitle1">响应结果:</Typography>
                                <Alert
                                    severity={slowResult.code === 200 ? 'success' : 'error'}
                                    sx={{mt: 1}}
                                >
                                    {slowTwoResult ? JSON.stringify(slowTwoResult, null, 2) : '未请求'}
                                </Alert>
                            </Box>
                        </CardContent>
                    </Card>
                </Grid>
            </Grid>

            {error && (
                <Alert severity="error" sx={{mt: 3}}>
                    {error}
                </Alert>
            )}
        </Box>
    );
};

export default Break;
