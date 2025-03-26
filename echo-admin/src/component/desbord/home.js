import React from 'react';
import { Box } from '@mui/material';
import Header from "./header";
import Slider from "./sidebar";
import Content from './content';

const Home = () => {
    const [selectedMenu, setSelectedMenu] = React.useState('服务注册');
    const [tabs, setTabs] = React.useState([
        { key: '服务注册', label: '服务注册', closeable: false }
    ]);
    const [activeTab, setActiveTab] = React.useState('服务注册');

    const handleMenuChange = (menu, label) => {
        setSelectedMenu(menu);
        // 检查tab是否已经存在
        if (!tabs.find(tab => tab.key === menu)) {
            setTabs([...tabs, { key: menu, label, closeable: true }]);
        }
        setActiveTab(menu);
    };

    const handleTabChange = (tabKey) => {
        setSelectedMenu(tabKey);
        setActiveTab(tabKey);
    };

    const handleTabClose = (tabKey) => {
        const newTabs = tabs.filter(tab => tab.key !== tabKey);
        setTabs(newTabs);
        // 如果关闭的是当前激活的tab，则激活最后一个tab
        if (tabKey === activeTab) {
            const lastTab = newTabs[newTabs.length - 1];
            setActiveTab(lastTab.key);
            setSelectedMenu(lastTab.key);
        }
    };

    return (
        <Box sx={{ display: 'flex', flexDirection: 'column', height: '100vh' }}>
            <Header />
            <Box sx={{ display: 'flex', flex: 1, overflow: 'hidden' }}>
                <Slider onMenuChange={handleMenuChange} />
                <Content
                    selectedMenu={selectedMenu}
                    tabs={tabs}
                    activeTab={activeTab}
                    onTabChange={handleTabChange}
                    onTabClose={handleTabClose}
                />
            </Box>
        </Box>
    );
};

export default Home;
