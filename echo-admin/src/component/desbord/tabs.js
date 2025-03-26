import React, { useState } from 'react';

const Tabs = ({ tabs }) => {
    const [activeTab, setActiveTab] = useState(tabs[0].key);
    return (
        <div>
            <ul className="tabs">
                {tabs.map(tab => (
                    <li key={tab.key} className={activeTab === tab.key ? 'active' : ''} onClick={() => setActiveTab(tab.key)}>
                        {tab.label}
                    </li>
                ))}
            </ul>
            <div className="tab-content">
                {tabs.map(tab => (
                    <div key={tab.key} style={{ display: activeTab === tab.key ? 'block' : 'none' }}>
                        {tab.content}
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Tabs;
