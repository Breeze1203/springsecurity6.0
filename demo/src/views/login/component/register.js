import React from 'react';
import { Form, Input, Button, Checkbox, FormItem } from 'element-react';
import 'element-react/dist/theme-chalk/index.css';

const Register = () => {
    return (
        <Form size="large" className="login-content-form">
            <FormItem className="login-animation1" label="Username">
                <Input prefix={<span className="el-input__icon"><i className="el-icon-user" /></>} placeholder="Username" />
            </FormItem>
            <FormItem className="login-animation2" label="Password">
                <Input prefix={<span className="el-input__icon"><i className="el-icon-lock" /></>} type="password" placeholder="Password" />
            </FormItem>
            <FormItem className="login-animation3" label="Phone">
                <Input prefix={<span className="el-input__icon"><i className="el-icon-location" /></>} placeholder="Phone" />
            </FormItem>
            <FormItem>
                <Checkbox>I agree to the terms and conditions</Checkbox>
                <Button link type="primary">Privacy Policy</Button>
            </FormItem>
            <FormItem className="login-animation4">
                <Button type="primary" className="login-content-submit">
                    <span>Register</span>
                </Button>
            </FormItem>
        </Form>
    );
};

export default Register;
