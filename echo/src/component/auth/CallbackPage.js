import { useEffect } from 'react';
import { useLocation, useNavigate } from 'react-router-dom';

const CallbackPage = () => {
    const location = useLocation();
    const navigate = useNavigate();

    useEffect(() => {
        // 从 URL 中获取查询参数
        const params = new URLSearchParams(location.search);
        const encodedJson = params.get('response');
        if (encodedJson) {
            try {
                // 解码并解析 JSON
                const jsonResponse = decodeURIComponent(encodedJson);
                const data = JSON.parse(jsonResponse);

                // 根据响应处理逻辑
                if (data.code === 200) {
                    console.log('登录成功:', data.data.username);

                    localStorage.setItem('username', data.data.username); // 存储用户信息
                    navigate('/home'); // 假设有主页，跳转
                } else if (data.code === 500) {
                    console.error('服务器错误:', data.message);
                    alert('登录失败：' + data.message);
                    navigate('/login'); // 返回登录页
                } else {
                    console.error('未知状态:', data.message);
                    navigate('/login');
                }
            } catch (error) {
                console.error('解析 JSON 失败:', error);
                alert('数据解析错误');
                navigate('/login');
            }
        } else {
            console.error('未收到响应数据');
            navigate('/login');
        }
    }, [location, navigate]);

    return <div>处理登录回调中...</div>;
};

export default CallbackPage;
