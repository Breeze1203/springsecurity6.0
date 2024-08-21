export function MyButtonTwo({count,onClick}) {
    return (
        <button className={"button"} onClick={onClick}>Clicked {count} times</button> // 使用导入的样式对象
    );
}
