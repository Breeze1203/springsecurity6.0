import React from 'react';
import { Form, FormItem, Input, Button, Col, Row } from 'element-react';
import 'element-react/dist/theme-chalk/index.css';

const LoginMobile = () => {
    return (
        <Form size="large" className="login-content-form">
            <FormItem className="login-animation1" label="Mobile">
                <Input
                    prefix={<span className="el-input__icon"><i className="iconfont icon-dianhua" /></>}
                    placeholder="Mobile"
                    clearable
                    autoComplete="off"
                />
            </FormItem>
            <FormItem className="login-animation2" label="Code">
                <Row gutter={10}>
                    <Col span={15}>
                        <Input
                            prefix={<span className="el-input__icon"><i className="el-icon-position" /></>}
                            placeholder="Code"
                            clearable
                            maxLength="6"
                            autoComplete="off"
                        />
                    </Col>
                    <Col span={8}>
                        <Button type="text">Send Code</Button>
                    </Col>
                </Row>
            </FormItem>
            <FormItem className="login-animation3">
                <Button type="primary">
                    <span>Login</span>
                </Button>
            </FormItem>
        </Form>
    );
};

export default LoginMobile;
