import React from 'react';
import { Card, Timeline, TimelineItem, Button } from 'element-react';
import 'element-react/dist/theme-chalk/index.css';

const SysLogDashboard = () => {
    return (
        <Card className="box-card" style={{ height: '100%' }}>
            <div className="card-header">
                <span>System Logs</span>
                <Button link className="button" onClick={() => {}}>
                    More
                </Button>
            </div>
            <Timeline>
                {/* 假设的数据，实际项目中应替换为从后端获取的数据 */}
                <TimelineItem timestamp="2023-04-01 12:00:00">
                    Log Title 1 - 192.168.1.1
                </TimelineItem>
                <TimelineItem timestamp="2023-04-01 12:05:00">
                    Log Title 2 - 192.168.1.2
                </TimelineItem>
                {/* 更多的 TimelineItem */}
            </Timeline>
        </Card>
    );
};

export default SysLogDashboard;
