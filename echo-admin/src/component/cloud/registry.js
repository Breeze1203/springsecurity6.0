import React, { useState, useEffect } from 'react';
import {
    Box,
    Paper,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    TextField,
    Typography,
    Chip,
    IconButton,
    Tooltip,
    InputAdornment,
    Popover,
    Snackbar, // 新增 Snackbar
    Alert // 新增 Alert
} from '@mui/material';
import {
    Search as SearchIcon,
    Refresh as RefreshIcon,
    CheckCircle as CheckCircleIcon,
    Cancel as CancelIcon,
    Link as LinkIcon,
    PlayArrow as PlayArrowIcon,
    Stop as StopIcon
} from '@mui/icons-material';
import { getAllRegistries,stopProvider,restartProvider } from '../../axios/api/registry';


const ServiceRegistry = () => {
    const [services, setServices] = useState([]);
    const [searchTerm, setSearchTerm] = useState('');
    const [filteredServices, setFilteredServices] = useState([]);
    const [anchorEl, setAnchorEl] = useState(null);
    const [selectedServiceMetadata, setSelectedServiceMetadata] = useState(null);
    const [snackbar, setSnackbar] = useState({
        open: false,
        message: '',
        severity: 'error'
    });

    useEffect(() => {
        fetchData();
    }, []);

    useEffect(() => {
        const filtered = Array.isArray(services) ? services.filter(service =>
            service.serviceId.toLowerCase().includes(searchTerm.toLowerCase()) ||
            service.instanceId.toLowerCase().includes(searchTerm.toLowerCase()) ||
            service.host.toLowerCase().includes(searchTerm.toLowerCase())
        ) : [];
        setFilteredServices(filtered);
    }, [searchTerm, services]);

    const fetchData = async () => {
        try {
            const response = await getAllRegistries();
            const data = Array.isArray(response.data) ? response.data : [];
            setServices(data);
            setFilteredServices(data);
        } catch (error) {
            setServices([]);
            setFilteredServices([]);
        }
    };

    const handleRefresh = () => {
        fetchData();
    };

    const handlePopoverOpen = (event, metadata) => {
        setAnchorEl(event.currentTarget);
        setSelectedServiceMetadata(metadata);
    };

    const handlePopoverClose = () => {
        setAnchorEl(null);
        setSelectedServiceMetadata(null);
    };

    const handleCloseSnackbar = () => {
        setSnackbar({ ...snackbar, open: false });
    };

    const handleStopProvider = async (serviceId) => {
        try {
            await stopProvider(serviceId);
            fetchData(); // 刷新数据
        } catch (error) {
            setSnackbar({
                open: true,
                message: `停止服务失败: ${error.message}`,
                severity: 'error'
            });
        }
    };

    const handleRestartProvider = async (serviceId) => {
        try {
            await restartProvider(serviceId);
            fetchData(); // 刷新数据
        } catch (error) {
            setSnackbar({
                open: true,
                message: `启动服务失败: ${error.message}`,
                severity: 'error'
            });
        }
    };

    const renderStatus = (status) => {
        return (
            <Chip
                icon={status === 'UP' ? <CheckCircleIcon /> : <CancelIcon />}
                label={status}
                color={status === 'UP' ? 'success' : 'error'}
                size="small"
                variant="outlined"
            />
        );
    };

    const renderActionButton = (service) => {
        if (service.status === 'UP') {
            return (
                <Tooltip title="停止服务">
                    <IconButton
                        size="small"
                        color="error"
                        onClick={() => handleStopProvider(service.serviceId)}
                    >
                        <StopIcon fontSize="small" />
                    </IconButton>
                </Tooltip>
            );
        } else if (service.status === 'DOWN') {
            return (
                <Tooltip title="启动服务">
                    <IconButton
                        size="small"
                        color="success"
                        onClick={() => handleRestartProvider(service.serviceId)}
                    >
                        <PlayArrowIcon fontSize="small" />
                    </IconButton>
                </Tooltip>
            );
        }
        return null;
    };

    const open = Boolean(anchorEl);

    return (
        <Box sx={{ height: '100%', display: 'flex', flexDirection: 'column' }}>
            <Box sx={{
                p: 2,
                display: 'flex',
                justifyContent: 'space-between',
                alignItems: 'center',
                backgroundColor: '#fff',
                borderRadius: '4px',
                mb: 2,
                boxShadow: '0 1px 2px 0 rgba(0, 0, 0, 0.03)'
            }}>
                <TextField
                    size="small"
                    placeholder="搜索服务ID/实例ID/主机"
                    value={searchTerm}
                    onChange={(e) => setSearchTerm(e.target.value)}
                    sx={{ width: 300 }}
                    InputProps={{
                        startAdornment: (
                            <InputAdornment position="start">
                                <SearchIcon sx={{ color: 'text.secondary' }} />
                            </InputAdornment>
                        ),
                    }}
                />
                <Tooltip title="刷新数据">
                    <IconButton onClick={handleRefresh}>
                        <RefreshIcon />
                    </IconButton>
                </Tooltip>
            </Box>
            <TableContainer
                component={Paper}
                sx={{
                    flex: 1,
                    maxHeight: '100%',
                    overflow: 'auto',
                    boxShadow: '0 1px 2px 0 rgba(0, 0, 0, 0.03)',
                }}
            >
                <Table stickyHeader>
                    <TableHead>
                        <TableRow>
                            <TableCell>状态</TableCell>
                            <TableCell>服务ID</TableCell>
                            <TableCell>实例ID</TableCell>
                            <TableCell>主机</TableCell>
                            <TableCell>端口</TableCell>
                            <TableCell>URI</TableCell>
                            <TableCell>安全状态</TableCell>
                            <TableCell>管理端口</TableCell>
                            <TableCell>详情</TableCell>
                            <TableCell>操作</TableCell>
                        </TableRow>
                    </TableHead>
                    <TableBody>
                        {Array.isArray(filteredServices) ? filteredServices.map((service) => (
                            <TableRow key={service.instanceId}>
                                <TableCell>{renderStatus(service.status)}</TableCell>
                                <TableCell>{service.serviceId}</TableCell>
                                <TableCell>{service.instanceId}</TableCell>
                                <TableCell>{service.host}</TableCell>
                                <TableCell>{service.port}</TableCell>
                                <TableCell>
                                    <Tooltip title={service.uri}>
                                        <span style={{
                                            cursor: 'pointer',
                                            color: '#1890ff',
                                            display: 'flex',
                                            alignItems: 'center',
                                            gap: '4px'
                                        }}>
                                            <LinkIcon fontSize="small" />
                                            {service.uri}
                                        </span>
                                    </Tooltip>
                                </TableCell>
                                <TableCell>
                                    <Chip
                                        label={service.secure ? "安全" : "非安全"}
                                        color={service.secure ? "success" : "default"}
                                        size="small"
                                    />
                                </TableCell>
                                <TableCell>{service.metadata["management.port"]}</TableCell>
                                <TableCell>
                                    <Tooltip title="查看详情">
                                        <IconButton
                                            size="small"
                                            color="primary"
                                            onClick={(event) => handlePopoverOpen(event, service.metadata)}
                                        >
                                            <SearchIcon fontSize="small" />
                                        </IconButton>
                                    </Tooltip>
                                </TableCell>
                                <TableCell>
                                    {renderActionButton(service)}
                                </TableCell>
                            </TableRow>
                        )) : null}
                    </TableBody>
                </Table>
            </TableContainer>
            {filteredServices.length === 0 && (
                <Box sx={{
                    display: 'flex',
                    justifyContent: 'center',
                    alignItems: 'center',
                    p: 3
                }}>
                    <Typography color="text.secondary">
                        没有找到匹配的服务实例
                    </Typography>
                </Box>
            )}
            <Popover
                open={open}
                anchorEl={anchorEl}
                onClose={handlePopoverClose}
                anchorOrigin={{
                    vertical: 'bottom',
                    horizontal: 'left',
                }}
                transformOrigin={{
                    vertical: 'top',
                    horizontal: 'left',
                }}
            >
                {selectedServiceMetadata && (
                    <Box sx={{ p: 2 }}>
                        {Object.entries(selectedServiceMetadata).map(([key, value]) => (
                            <Typography key={key}>{`${key}: ${value}`}</Typography>
                        ))}
                    </Box>
                )}
            </Popover>
            <Snackbar
                open={snackbar.open}
                autoHideDuration={6000}
                onClose={handleCloseSnackbar}
                anchorOrigin={{ vertical: 'top', horizontal: 'center' }}
            >
                <Alert
                    onClose={handleCloseSnackbar}
                    severity={snackbar.severity}
                    sx={{ width: '100%' }}
                >
                    {snackbar.message}
                </Alert>
            </Snackbar>
        </Box>
    );
};

export default ServiceRegistry;
