import React from 'react';
import { Form, FormItem, Input, Button, Col, Row } from 'element-react';
import 'element-react/dist/theme-chalk/index.css';

const PasswordLogin = () => {
    return (
        <Form size="large" className="login-content-form">
            <FormItem className="login-animation1" label="Username">
                <Input
                    prefix={<span className="el-input__icon"><i className="el-icon-user" /></>}
                    placeholder="Username"
                    clearable
                    autoComplete="off"
                />
            </FormItem>
            <FormItem className="login-animation2" label="Password">
                <Input
                    type="password"
                    placeholder="Password"
                    clearable
                    autoComplete="off"
                />
                <i className="iconfont el-input__icon login-content-password">
                    icon-yincangmima
                </i>
            </FormItem>
            <FormItem className="login-animation2" label="Code">
                <Row gutter={10}>
                    <Col span={15}>
                        <Input
                            prefix={<span className="el-input__icon"><i className="el-icon-location" /></>}
                            placeholder="Code"
                            clearable
                            autoComplete="off"
                        />
                    </Col>
                    <Col span={8}>
                        <img src="path-to-verify-image" onClick={() => {}} />
                    </Col>
                </Row>
            </FormItem>
            <FormItem className="login-animation4">
                <Button type="primary" className="login-content-submit">
                    <span>Login</span>
                </Button>
            </FormItem>
            <div className="font12 mt30 login-animation4 login-msg">
                Message
            </div>
        </Form>
    );
};

export default PasswordLogin;
