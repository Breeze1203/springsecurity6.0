import React from 'react';
import {Box,List, ListItem, ListItemIcon, ListItemText } from '@mui/material';
import {Bolt, Cloud, Dashboard, Equalizer, Event, Settings, Sync, Timeline} from "@mui/icons-material";



const Sidebar = ({ onMenuChange }) => {
    const menuItems = [
        {text: '服务注册',value: '服务注册', icon: <Cloud/>},
        {text: '网关服务', value: '网关服务',icon: <Dashboard/>},
        {text: '消息总线',value: '消息总线', icon: <Sync/>},
        {text: '断路器',value: '断路器',icon: <Bolt/>},
        {text: '事件驱动', value: '事件驱动',icon: <Event/>},
        {text: '链路追踪', value: '链路追踪',icon: <Timeline/>},
        {text: '负载均衡',value: '负载均衡', icon: <Equalizer/>},
        {text: '服务配置',value: '服务配置', icon: <Settings/>},
    ];

    return (
        <Box sx={{
            width: 240,
            backgroundColor: '#fff',
            borderRight: '1px solid rgba(0, 0, 0, 0.12)',
        }}>
            <List>
                {menuItems.map((item) => (
                    <ListItem
                        button
                        key={item.value}
                        onClick={() => onMenuChange(item.value, item.text)}
                    >
                        <ListItemIcon>{item.icon}</ListItemIcon>
                        <ListItemText primary={item.text} />
                    </ListItem>
                ))}
            </List>
        </Box>
    );
};

export default Sidebar;
