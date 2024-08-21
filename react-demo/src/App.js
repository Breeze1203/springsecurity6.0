import './App.css';
import MyButton from "./demo/button";
import Profile from "./demo/image";
import {Condition} from "./demo/condition";
import ShoppingList from "./components/Products";
import {useState} from "react";
import {MyButtonTwo} from "./components/ButtonTwo";
import TaskList from "./components/TaskList";
import TaskApp from "./components/TaskApp";
import Section from "./components/context/Sections";
import Heading from "./components/context/Heading";
import ImageOne from "./components/image/ImageOne";
import ImageTwo from "./components/image/ImageTwo";
import Stopwatch from "./components/ref/Stopwatch";

function App() {
    const [count, setCount] = useState(0);
    function handleClick() {
        setCount(count + 2);
    }

    return (
        <div>
            <h1 onClick={handleClick}>Welcome to my app {count}</h1>
            {/*<MyButton/>*/}
            {/*<MyButtonTwo count={count} onClick={handleClick}/>*/}
            {/*<Profile/>*/}
            {/*<Condition/>*/}
            {/*<ShoppingList/>*/}
            <TaskApp/>
            <ImageTwo/>
            <Stopwatch/>
            {/*<ImageOne/>*/}
            {/*<AllPosts/>*/}
        </div>
    );

}

function AllPosts() {
    return (
        <Section>
            <RecentPosts />
        </Section>
    );
}

function RecentPosts() {
    return (
        <Section>
            <Heading>最近的帖子</Heading>
            <Post
                title="里斯本的味道"
                body="...那些蛋挞！"
            />
            <Post
                title="探戈节奏中的布宜诺斯艾利斯"
                body="我爱它！"
            />
        </Section>
    );
}

function Post({ title, body }) {
    return (
        <Section isFancy={true}>
            <Heading>
                {title}
            </Heading>
            <p><i>{body}</i></p>
        </Section>
    );
}


export default App;
