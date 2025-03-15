import React, {useState, useEffect} from 'react';
import {
    Container,
    Tabs,
    Tab,
    TextField,
    Button,
    Checkbox,
    FormControlLabel,
    Typography,
    Box,
} from '@mui/material';
import {Link} from 'react-router-dom';
import {styled} from '@mui/system';
import PersonIcon from '@mui/icons-material/Person';
import LockIcon from '@mui/icons-material/Lock';
import axios from 'axios';
import backgroundImage from '../../assets/background.jpeg'; // 相对路径，从 auth 到 assets

// 自定义样式
const LoginContainer = styled(Container)({
    display: 'flex',
    height: '100vh',
    background: `url(${backgroundImage}) no-repeat left center`, // 使用导入的图片
    backgroundSize: '50%', // 确保图片覆盖整个容器
    '@media (max-width: 768px)': {
        flexDirection: 'column',
    },
});

const IllustrationBox = styled(Box)({
    flex: 1,
    background: `url('/login-illustration.png') no-repeat center center`,
    backgroundSize: 'cover',
    '@media (max-width: 768px)': {
        display: 'none', // 小屏幕隐藏插图
    },
});

const FormBox = styled(Box)({
    flex: 1,
    display: 'flex',
    flexDirection: 'column',
    justifyContent: 'center',
    padding: '0 50px',
    backgroundColor: '#fff',
    boxShadow: '0 4px 8px rgba(0, 0, 0, 0.1)',
    borderRadius: '10px',
    margin: 'auto',
    maxWidth: '400px',
    '@media (max-width: 768px)': {
        padding: '20px',
        maxWidth: '100%',
    },
});

const TabPanelBox = styled(Box)({
    minHeight: '180px', // 固定高度，避免切换时跳动
    transition: 'all 0.3s ease-in-out', // 添加过渡动画
});

const LoginButton = styled(Button)({
    background: 'linear-gradient(90deg, #1976D2 0%, #42A5F5 100%)', // 蓝色渐变
    color: '#fff',
    borderRadius: '25px',
    padding: '12px 0',
    fontWeight: 'bold',
    fontSize: '1rem',
    textTransform: 'uppercase',
    transition: 'all 0.2s ease-in-out',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
    '&:hover': {
        transform: 'scale(1.05)',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
        background: 'linear-gradient(90deg, #1565C0 0%, #2196F3 100%)', // 悬停加深蓝色
    },
    '&:disabled': {
        background: '#B0BEC5',
        color: '#fff',
        opacity: 0.6,
        boxShadow: 'none',
    },
});

const OAuthButton = styled(Button)({
    background: 'linear-gradient(90deg, #B0BEC5 0%, #CFD8DC 100%)', // 灰色渐变
    color: '#fff',
    borderRadius: '25px',
    fontWeight: 'bold',
    fontSize: '0.95rem',
    textTransform: 'uppercase',
    transition: 'all 0.2s ease-in-out',
    marginBottom: '20px',
    boxShadow: '0 2px 4px rgba(0, 0, 0, 0.1)',
    marginTop: '5px',
    '&:hover': {
        transform: 'scale(1.05)',
        boxShadow: '0 4px 8px rgba(0, 0, 0, 0.2)',
        background: 'linear-gradient(90deg, #A0AEB5 0%, #B0BEC5 100%)', // 悬停加深灰色
    },
    '&:disabled': {
        background: '#B0BEC5',
        color: '#fff',
        opacity: 0.6,
        boxShadow: 'none',
    },
});

const SendCodeButton = styled(Button)({
    color: 'rgba(0,0,0)',
    fontWeight: 'bold',
    fontSize: '0.8rem',
    borderRadius: '10px', // 稍微减小圆角，与高度协调
    textTransform: 'uppercase',
    transition: 'all 0.3s ease-in-out',
});

const LogoTypography = styled(Typography)({
    color: '#006064',
    fontWeight: 'bold',
    fontSize: '2rem',
    marginTop: '10px',
});


const IconWrapper = styled(Box)({
    display: 'flex',
    alignItems: 'center',
    marginRight: '10px',
    color: '#757575',
});

const CustomDivider = styled(Box)({
    textAlign: 'center',
    color: '#006064',
    fontSize: '1rem',
});

const LoginPage = () => {
    const [tabValue, setTabValue] = useState(0);
    const [username, setUsername] = useState('');
    const [password, setPassword] = useState('');
    const [phone, setPhone] = useState('');
    const [code, setCode] = useState('');
    const [rememberMe, setRememberMe] = useState(false);
    const [countdown, setCountdown] = useState(0); // 倒计时状态
    const [isButtonDisabled, setIsButtonDisabled] = useState(false); // 按钮禁用状态

    // 倒计时逻辑
    useEffect(() => {
        let timer;
        if (countdown > 0) {
            timer = setInterval(() => {
                setCountdown((prev) => {
                    if (prev <= 1) {
                        setIsButtonDisabled(false);
                        clearInterval(timer);
                        return 0;
                    }
                    return prev - 1;
                });
            }, 1000);
        }
        return () => clearInterval(timer); // 组件卸载时清理定时器
    }, [countdown]);

    const handleTabChange = (event, newValue) => {
        setTabValue(newValue);
    };

    const handleUsernameLogin = async () => {
        try {
            const response = await axios.post('/api/login', {username, password});
            const token = response.data.token;
            if (rememberMe) {
                localStorage.setItem('token', token);
            } else {
                sessionStorage.setItem('token', token);
            }
            alert('登录成功！');
        } catch (error) {
            alert('登录失败，请检查用户名或密码');
        }
    };

    const handleSendCode = async () => {
        try {
            await axios.post('/api/send-sms', {phone});
            alert('验证码已发送！');
            setCountdown(60); // 设置倒计时为 60 秒
            setIsButtonDisabled(true); // 禁用按钮
        } catch (error) {
            alert('验证码发送失败');
        }
    };

    const handleSmsLogin = async () => {
        try {
            const response = await axios.post('/api/sms-login', {phone, code});
            const token = response.data.token;
            if (rememberMe) {
                localStorage.setItem('token', token);
            } else {
                sessionStorage.setItem('token', token);
            }
            alert('登录成功！');
        } catch (error) {
            alert('登录失败，请检查验证码');
        }
    };

    const handleOAuthLogin = async () => {
        window.location.href = 'http://localhost:5555/oauth2/authorize/echo';
    };

    // 公共表单底部组件
    const FormFooter = ({onLogin}) => (
        <>
            <Box sx={{display: 'flex', justifyContent: 'space-between', alignItems: 'center', mb: 2}}>
                <FormControlLabel
                    control={
                        <Checkbox
                            checked={rememberMe}
                            onChange={(e) => setRememberMe(e.target.checked)}
                        />
                    }
                    label="记住我"
                />
                <Link sx={{color: 'red'}} to="/forgot">
                    忘记密码
                </Link>
            </Box>
            <LoginButton fullWidth onClick={onLogin} sx={{mt: 2}}>
                LOGIN
            </LoginButton>
            <CustomDivider>OR</CustomDivider>
            <OAuthButton fullWidth onClick={handleOAuthLogin}>
                ECHO SERVER
            </OAuthButton>
        </>
    );

    return (
        <LoginContainer>
            {/* 左侧插图 */}
            <IllustrationBox/>

            {/* 右侧表单 */}
            <FormBox>
                {/* Logo */}
                <LogoTypography align="center">Echo Admin</LogoTypography>

                {/* 选项卡 */}
                <Tabs value={tabValue} onChange={handleTabChange} centered>
                    <Tab label="用户名登录"/>
                    <Tab label="验证码登录"/>
                </Tabs>

                {/* Tab 内容 */}
                <TabPanelBox>
                    {/* 用户名登录 */}
                    {tabValue === 0 && (
                        <Box>
                            <TextField
                                label="用户名"
                                fullWidth
                                value={username}
                                onChange={(e) => setUsername(e.target.value)}
                                margin="normal"
                                InputProps={{
                                    startAdornment: (
                                        <IconWrapper>
                                            <PersonIcon/>
                                        </IconWrapper>
                                    ),
                                }}
                                sx={{mb: 2}}
                            />
                            <TextField
                                label="密码"
                                type="password"
                                fullWidth
                                value={password}
                                onChange={(e) => setPassword(e.target.value)}
                                margin="normal"
                                InputProps={{
                                    startAdornment: (
                                        <IconWrapper>
                                            <LockIcon/>
                                        </IconWrapper>
                                    ),
                                }}
                                sx={{mb: 2}}
                            />
                            <FormFooter onLogin={handleUsernameLogin}/>
                        </Box>
                    )}

                    {/* 验证码登录 */}
                    {tabValue === 1 && (
                        <Box>
                            <TextField
                                label="手机号"
                                fullWidth
                                value={phone}
                                onChange={(e) => setPhone(e.target.value)}
                                margin="normal"
                                sx={{mb: 2}}
                            />
                            <Box sx={{display: 'flex', gap: 2, mb: 2}}>
                                <TextField
                                    label="验证码"
                                    fullWidth
                                    value={code}
                                    onChange={(e) => setCode(e.target.value)}
                                    margin="normal"
                                />
                                <SendCodeButton onClick={handleSendCode} disabled={isButtonDisabled}>
                                    {isButtonDisabled ? `${countdown}s 后重试` : '获取验证码'}
                                </SendCodeButton>
                            </Box>
                            <FormFooter onLogin={handleSmsLogin}/>
                        </Box>
                    )}
                </TabPanelBox>
            </FormBox>
        </LoginContainer>
    );
};

export default LoginPage;
