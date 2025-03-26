import React, { useState } from 'react';
import {useNavigate } from 'react-router-dom';
import {
    AppBar,
    Toolbar,
    Typography,
    IconButton,
    Box,
    Avatar,
    Menu,
    MenuItem,
    Badge,
    Divider,
    ListItemIcon,
    Popover,
} from '@mui/material';
import {
    Notifications as NotificationsIcon,
    Fullscreen as FullscreenIcon,
    FullscreenExit as FullscreenExitIcon,
    Brightness4 as DarkModeIcon,
    ExitToApp as LogoutIcon,
    Person as PersonIcon,
    Settings as SettingsIcon,
} from '@mui/icons-material';

const Header = () => {
    const navigate = useNavigate();
    const [anchorEl, setAnchorEl] = useState(null);
    const [notificationAnchor, setNotificationAnchor] = useState(null);
    const [isFullscreen, setIsFullscreen] = useState(false);
    const [isDarkMode, setIsDarkMode] = useState(false);

    // 处理用户菜单
    const handleMenuOpen = (event) => {
        setAnchorEl(event.currentTarget);
    };

    const handleMenuClose = () => {
        setAnchorEl(null);
    };

    // 处理通知菜单
    const handleNotificationOpen = (event) => {
        setNotificationAnchor(event.currentTarget);
    };

    const handleNotificationClose = () => {
        setNotificationAnchor(null);
    };

    // 处理全屏
    const toggleFullscreen = () => {
        if (!document.fullscreenElement) {
            document.documentElement.requestFullscreen();
            setIsFullscreen(true);
        } else {
            if (document.exitFullscreen) {
                document.exitFullscreen();
                setIsFullscreen(false);
            }
        }
    };

    // 处理主题切换
    const handleThemeChange = () => {
        setIsDarkMode(!isDarkMode);
        // 这里可以添加切换主题的逻辑
    };

    // 处理退出登录
    const handleLogout = () => {
        // 添加退出登录逻辑
        localStorage.removeItem("username")
        navigate('/');
    };

    // 模拟通知数据
    const notifications = [
        { id: 1, text: '您有一个新的任务待处理', time: '10分钟前' },
        { id: 2, text: '系统更新提醒', time: '1小时前' },
        { id: 3, text: '服务器状态报告', time: '2小时前' },
    ];

    return (
        <AppBar
            position="static"
            sx={{
                backgroundColor: '#ffffff',    // 纯白色背景
                color: '#52525b',             // 深灰色文字
                boxShadow: '0 1px 4px rgba(0,21,41,.08)',
                borderBottom: '1px solid #e5e7eb',  // 添加浅灰色边框增加分隔感
            }}
        >
            <Toolbar>
                {/* Logo和标题 */}
                <Typography
                    variant="h6"
                    component="div"
                    sx={{
                        flexGrow: 1,
                        color: '#001529',
                        fontWeight: 600,
                        fontSize: '18px',
                        fontFamily: '"Helvetica Neue", Arial, sans-serif'
                    }}
                >
                    Echo Admin
                </Typography>

                {/* 功能按钮区域 */}
                <Box sx={{ display: 'flex', alignItems: 'center', gap: 1 }}>
                    {/* 通知铃铛 */}
                    <IconButton
                        color="inherit"
                        onClick={handleNotificationOpen}
                        sx={{ color: '#595959' }}
                    >
                        <Badge badgeContent={notifications.length} color="error">
                            <NotificationsIcon />
                        </Badge>
                    </IconButton>

                    {/* 全屏切换 */}
                    <IconButton
                        color="inherit"
                        onClick={toggleFullscreen}
                        sx={{ color: '#595959' }}
                    >
                        {isFullscreen ? <FullscreenExitIcon /> : <FullscreenIcon />}
                    </IconButton>

                    {/* 主题切换 */}
                    <IconButton
                        color="inherit"
                        onClick={handleThemeChange}
                        sx={{ color: '#595959' }}
                    >
                        <DarkModeIcon />
                    </IconButton>

                    {/* 用户头像和菜单 */}
                    <IconButton
                        onClick={handleMenuOpen}
                        sx={{
                            padding: 0.5,
                            marginLeft: 1
                        }}
                    >
                        <Avatar
                            sx={{
                                width: 35,
                                height: 35,
                                border: '2px solid #1890ff'
                            }}
                        >
                            B
                        </Avatar>
                    </IconButton>
                </Box>

                {/* 用户菜单 */}
                <Menu
                    anchorEl={anchorEl}
                    open={Boolean(anchorEl)}
                    onClose={handleMenuClose}
                    transformOrigin={{ horizontal: 'right', vertical: 'top' }}
                    anchorOrigin={{ horizontal: 'right', vertical: 'bottom' }}
                >
                    <Box sx={{ px: 2, py: 1 }}>
                        <Typography variant="subtitle1" sx={{ fontWeight: 500 }}>
                            Breeze1203
                        </Typography>
                        <Typography variant="body2" color="text.secondary">
                            管理员
                        </Typography>
                    </Box>
                    <Divider />
                    <MenuItem onClick={handleMenuClose}>
                        <ListItemIcon>
                            <PersonIcon fontSize="small" />
                        </ListItemIcon>
                        个人中心
                    </MenuItem>
                    <MenuItem onClick={handleMenuClose}>
                        <ListItemIcon>
                            <SettingsIcon fontSize="small" />
                        </ListItemIcon>
                        个人设置
                    </MenuItem>
                    <Divider />
                    <MenuItem onClick={handleLogout}>
                        <ListItemIcon>
                            <LogoutIcon fontSize="small" />
                        </ListItemIcon>
                        退出登录
                    </MenuItem>
                </Menu>

                {/* 通知弹出框 */}
                <Popover
                    open={Boolean(notificationAnchor)}
                    anchorEl={notificationAnchor}
                    onClose={handleNotificationClose}
                    anchorOrigin={{
                        vertical: 'bottom',
                        horizontal: 'right',
                    }}
                    transformOrigin={{
                        vertical: 'top',
                        horizontal: 'right',
                    }}
                >
                    <Box sx={{ width: 300, maxHeight: 400, p: 0 }}>
                        <Box sx={{ p: 2, borderBottom: '1px solid #f0f0f0' }}>
                            <Typography variant="h6" sx={{ fontSize: '16px' }}>
                                通知中心
                            </Typography>
                        </Box>
                        {notifications.map((notification) => (
                            <MenuItem key={notification.id} sx={{ py: 2 }}>
                                <Box>
                                    <Typography variant="body2">
                                        {notification.text}
                                    </Typography>
                                    <Typography
                                        variant="caption"
                                        color="text.secondary"
                                        sx={{ mt: 0.5 }}
                                    >
                                        {notification.time}
                                    </Typography>
                                </Box>
                            </MenuItem>
                        ))}
                        <Box
                            sx={{
                                p: 1,
                                borderTop: '1px solid #f0f0f0',
                                textAlign: 'center'
                            }}
                        >
                            <Typography
                                variant="body2"
                                sx={{
                                    color: '#1890ff',
                                    cursor: 'pointer'
                                }}
                            >
                                查看全部
                            </Typography>
                        </Box>
                    </Box>
                </Popover>
            </Toolbar>
        </AppBar>
    );
};

export default Header;
