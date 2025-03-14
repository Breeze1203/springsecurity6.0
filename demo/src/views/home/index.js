import React from 'react';
import { SplitPane } from 'element-react';
import 'element-react/dist/theme-chalk/index.css';

const Home = () => {
    return (
        <div className="layout-padding">
            <div className="layout-padding-auto layout-padding-view">
                <SplitPane>
                    <SplitPane Pane1Style={{ flex: '70%' }}>
                        <SplitPane orientation="horizontal">
                            <SplitPane Pane1Style={{ flex: '25%' }}>
                                <div className="pane-content">CurrentUser</div>
                            </SplitPane>
                            <SplitPane Pane2Style={{ flex: '75%' }}>
                                <div className="pane-content">Favorite</div>
                            </SplitPane>
                        </SplitPane>
                    </SplitPane>
                    <SplitPane Pane2Style={{ flex: '30%' }}>
                        <SplitPane orientation="horizontal">
                            <SplitPane Pane1Style={{ flex: '58%' }}>
                                <div className="pane-content">Schedule</div>
                            </SplitPane>
                            <SplitPane Pane2Style={{ flex: '42%' }}>
                                <div className="pane-content">SysLog</div>
                            </SplitPane>
                        </SplitPane>
                    </SplitPane>
                </SplitPane>
            </div>
        </div>
    );
};

export default Home;
