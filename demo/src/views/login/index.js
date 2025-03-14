import React from 'react';
import { Tabs, TabPane } from 'element-react';
import 'element-react/dist/theme-chalk/index.css';
import illustration from './assets/login/login_bg.svg';
import bg from './assets/login/bg.png';
import Password from './component/password';
import Mobile from './component/mobile';
import Register from './component/register';

const LoginIndex = () => {
    return (
        <div className="select-none">
            <img src={bg} className="wave" />
            <div className="flex-c absolute right-5 top-3"></div>
            <div className="login-container">
                <div className="img">
                    <img src={illustration} />
                </div>
                <div className="login-box">
                    <div className="login-form">
                        <Tabs>
                            <TabPane label="Account Password Login" name="account">
                                <Password />
                            </TabPane>
                            <TabPane label="Mobile Login" name="mobile">
                                <Mobile />
                            </TabPane>
                            <TabPane label="Register" name="register">
                                <Register />
                            </TabPane>
                        </Tabs>
                    </div>
                </div>
            </div>
        </div>
    );
};

export default LoginIndex;
