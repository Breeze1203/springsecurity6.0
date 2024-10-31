import React, { useState, useEffect } from 'react';
import { Card, Timeline, TimelineItem, Button } from 'element-react';
import 'element-react/dist/theme-chalk/index.css';
import { useTranslation } from 'react-i18next';

import { pageList } from './api/admin/log'; // 假设你已经创建了相应的 API 调用

const SysLogDashboard = () => {
    const { t } = useTranslation();
    const [logState, setLogState] = useState({
        dataList: [],
        descs: ['create_time'],
        pagination: {
            size: 3,
        },
    });

    const [loading, setLoading] = useState(false);

    useEffect(() => {
        const fetchData = async () => {
            setLoading(true);
            try {
                const data = await pageList();
                setLogState(prevState => ({ ...prevState, dataList: data }));
            } catch (error) {
                console.error('Error fetching log data:', error);
            } finally {
                setLoading(false);
            }
        };
        fetchData();
    }, []);

    const handleRoutr = () => {
        // Assuming you have a router setup
        router.push('/admin/log/index');
    };

    return (
        <Card className="box-card" style={{ height: '100%' }}>
            {({ header }) => (
                <>
                    <div className="card-header">
                        <span>{t('home.systemLogsTip')}</span>
                        <Button link className="button" onClick={handleRoutr}>
                            {t('home.moreTip')}
                        </Button>
                    </div>
                </>
            )}
            {logState.dataList.length > 0 && (
                <Timeline>
                    {logState.dataList.map((item, index) => (
                        <TimelineItem key={index} timestamp={item.createTime}>
                            {item.title} - {item.remoteAddr}
                        </TimelineItem>
                    ))}
                </Timeline>
            )}
        </Card>
    );
};

const styles = {
    cardHeader: {
        display: 'flex',
        justifyContent: 'space-between',
        alignItems: 'center',
    },
};

export default SysLogDashboard;
