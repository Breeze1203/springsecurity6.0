import "./button.css"
import {useState} from "react";


function MyButton() {
    const [count, setCount] = useState(0);

    function handleClick() {
        setCount(count + 1);
    }
    return (
        <button className={"button"} onClick={handleClick}>Clicked {count} times</button> // 使用导入的样式对象
    );
}

export default MyButton;
