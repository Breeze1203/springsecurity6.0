import React from 'react';
import { Card, Avatar } from 'element-react';
import 'element-react/dist/theme-chalk/index.css';

const CurrentUser = () => {
    return (
        <Card style={{ height: '100%' }}>
            <div style={{ display: 'flex', justifyContent: 'space-between' }}>
                <div style={{ display: 'flex', alignItems: 'center' }}>
                    <Avatar style={{ width: 60, height: 60, shape: 'circle' }} size={100} fit="cover" src="path-to-avatar" />
                    <div className="info">
                        <span style={{ fontWeight: 600, margin: '2px 0', fontSize: 18 }}>User Name</span>
                        <span style={{ color: '#6d737b', margin: '2px 0' }}>Department | Position</span>
                    </div>
                </div>
                <span style={{ margin: 2 }}>Time</span>
            </div>
        </Card>
    );
};

export default CurrentUser;
