import React from 'react';
import { Box, Tab, Tabs, IconButton } from '@mui/material';
import CloseIcon from '@mui/icons-material/Close';
import ServiceRegistry from "../cloud/registry";
import GateWay from "../cloud/gateway";
import Stream from "../cloud/stream";
import Break from "../cloud/break";
import Bus from "../cloud/bus";
import TraceViewer from "../cloud/trance";
import Loadbanlance from "../cloud/loadbanlance";

const Content = ({ selectedMenu, tabs, activeTab, onTabChange, onTabClose }) => {
    const renderContent = () => {
        switch (selectedMenu) {
            case '服务注册':
                return <ServiceRegistry/>;
            case '网关服务':
                return <GateWay/>;
            case '断路器':
                return <Break/>;
            case '事件驱动':
                return <Stream/>;
            case '消息总线':
                return <Bus/>;
            case '链路追踪':
                return <TraceViewer/>;
            case '负载均衡':
                return <Loadbanlance/>;
            case '服务配置':
                return <Bus/>;
        }
    };

    return (
        <Box sx={{
            flex: 1,
            display: 'flex',
            flexDirection: 'column',
            overflow: 'hidden',
            backgroundColor: '#f0f2f5',  // 灰色背景
        }}>
            <Box sx={{
                borderBottom: 1,
                borderColor: 'divider',
                backgroundColor: '#ffffff',  // 白色背景
                borderRadius: '4px',        // 圆角
                boxShadow: '0 1px 2px 0 rgba(0, 0, 0, 0.03)',  // 轻微阴影
            }}>
                <Tabs
                    value={activeTab}
                    onChange={(e, newValue) => onTabChange(newValue)}
                    variant="scrollable"
                    scrollButtons="auto"
                >
                    {tabs.map((tab) => (
                        <Tab
                            key={tab.key}
                            label={
                                <Box sx={{ display: 'flex', alignItems: 'center' }}>
                                    {tab.label}
                                    {tab.closeable && (
                                        <IconButton
                                            size="small"
                                            sx={{ ml: 1 }}
                                            onClick={(e) => {
                                                e.stopPropagation();
                                                onTabClose(tab.key);
                                            }}
                                        >
                                            <CloseIcon fontSize="small" />
                                        </IconButton>
                                    )}
                                </Box>
                            }
                            value={tab.key}
                        />
                    ))}
                </Tabs>
            </Box>
            <Box sx={{
                flex: 1,
                overflow: 'auto',
                backgroundColor: '#ffffff', // 白色背景
                borderRadius: '4px',       // 圆角
                boxShadow: '0 1px 2px 0 rgba(0, 0, 0, 0.03)',  // 轻微阴影
                margin: '10px',
            }}>
                {renderContent()}
            </Box>
        </Box>
    );
};

export default Content;
