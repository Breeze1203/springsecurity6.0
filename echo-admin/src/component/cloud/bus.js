import React, { useEffect, useState } from 'react';
import {
    Card,
    CardContent,
    Typography,
    Button,
    CircularProgress,
    Alert,
    Grid,
    Box,
    Divider,
    Chip
} from '@mui/material';
import { initBusConfig } from "../../axios/api/bus";

// Enhanced Config Display Card
function ConfigDisplayCard({ serviceName }) {
    const [config, setConfig] = useState(null); // 只维护当前环境的配置
    const [loading, setLoading] = useState(false);
    const [status, setStatus] = useState('');
    const env = serviceName === "bus-service-prod" ? "prod" : "dev"; // 根据 serviceName 确定环境

    // Fetch initial configuration
    useEffect(() => {
        fetchConfig();
    }, [env]); // 添加 env 作为依赖，确保环境变化时重新获取

    // Fetch configuration function
    const fetchConfig = async () => {
        setLoading(true);
        setStatus('');
        try {
            const response = await initBusConfig(env); // 只获取当前环境的配置
            setConfig(response.data); // 存储返回的配置数据
            setStatus('配置加载成功');
        } catch (error) {
            setStatus('配置加载失败: ' + error.message);
        } finally {
            setLoading(false);
        }
    };

    // Format configuration for display
    const renderConfigContent = () => {
        if (!config) return null;

        return (
            <Box sx={{ mt: 2 }}>
                <Box sx={{ mb: 2 }}>
                    <Typography variant="subtitle2" color="text.secondary">
                        服务环境: <Chip label={env.toUpperCase()} color="primary" size="small" />
                    </Typography>
                </Box>
                <Divider />
                <Box sx={{ mt: 2 }}>
                    {/* Eureka 配置 */}
                    <Typography variant="subtitle2" sx={{ mt: 2, mb: 1 }}>Eureka 配置</Typography>
                    <Typography variant="body2">
                        <strong>服务地址:</strong> {config.eurekaServiceUrl || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>优先使用IP:</strong> {String(config.preferIpAddress) || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>实例ID:</strong> {config.instanceId || '未配置'}
                    </Typography>

                    {/* MySQL 配置 */}
                    <Typography variant="subtitle2" sx={{ mt: 2, mb: 1 }}>MySQL 配置</Typography>
                    <Typography variant="body2">
                        <strong>用户名:</strong> {config.datasourceUsername || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>密码:</strong> {config.datasourcePassword || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>驱动类:</strong> {config.datasourceDriverClassName || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>数据库URL:</strong> {config.datasourceUrl || '未配置'}
                    </Typography>

                    {/* Redis 配置 */}
                    <Typography variant="subtitle2" sx={{ mt: 2, mb: 1 }}>Redis 配置</Typography>
                    <Typography variant="body2">
                        <strong>主机:</strong> {config.redisHost || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>端口:</strong> {config.redisPort || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>密码:</strong> {config.redisPassword || '未配置'}
                    </Typography>

                    {/* MongoDB 配置 */}
                    <Typography variant="subtitle2" sx={{ mt: 2, mb: 1 }}>MongoDB 配置</Typography>
                    <Typography variant="body2">
                        <strong>主机:</strong> {config.mongodbHost || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>端口:</strong> {config.mongodbPort || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>数据库:</strong> {config.mongodbDatabase || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>用户名:</strong> {config.mongodbUsername || '未配置'}
                    </Typography>
                    <Typography variant="body2">
                        <strong>密码:</strong> {config.mongodbPassword || '未配置'}
                    </Typography>
                </Box>
            </Box>
        );
    };

    return (
        <Card sx={{
            mb: 3,
            height: '100%',
            display: 'flex',
            flexDirection: 'column',
            boxShadow: 3,
            transition: 'all 0.3s',
            '&:hover': {
                boxShadow: 6,
            }
        }}>
            <CardContent sx={{ flexGrow: 1 }}>
                <Box sx={{ display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2 }}>
                    <Typography variant="h6" color="primary">
                        {env.toUpperCase()} 环境配置
                    </Typography>
                    <Button
                        variant="outlined"
                        size="small"
                        onClick={fetchConfig}
                        disabled={loading}
                    >
                        {loading ? <CircularProgress size={20} /> : '刷新'}
                    </Button>
                </Box>

                {loading && !config ? (
                    <Box sx={{ display: 'flex', justifyContent: 'center', my: 4 }}>
                        <CircularProgress />
                    </Box>
                ) : (
                    renderConfigContent()
                )}

                {status && (
                    <Alert
                        severity={status.includes('成功') ? 'success' : 'error'}
                        sx={{ mt: 2 }}
                    >
                        {status}
                    </Alert>
                )}
            </CardContent>
        </Card>
    );
}

// Main Component
export default function Bus() {
    return (
        <Box sx={{ p: 3 }}>
            <Typography variant="h4" gutterBottom sx={{ mb: 4 }}>
                消息总线环境配置属性配置监控
            </Typography>
            <Typography variant="body2" color="text.secondary" sx={{ mb: 4 }}>
                实时展示生产环境和开发环境的配置信息，修改配置文件后，提交到远程仓库，自动刷新
            </Typography>
            <Grid container spacing={3}>
                <Grid item xs={12} md={6}>
                    <ConfigDisplayCard serviceName="bus-service-prod" />
                </Grid>
                <Grid item xs={12} md={6}>
                    <ConfigDisplayCard serviceName="bus-service-dev" />
                </Grid>
            </Grid>
       </Box>
    );
}
