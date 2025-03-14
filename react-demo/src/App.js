import './App.css';
import MyButton from "./demo/button";
import Profile from "./demo/image";
import {Condition} from "./demo/condition";
import ShoppingList from "./components/Products";
import {useState} from "react";
import {MyButtonTwo} from "./components/ButtonTwo";
import TaskList from "./components/TaskList";
import {About} from "./components/About";
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
            <MyButton/>
            <MyButtonTwo count={count} onClick={handleClick}/>
            <App/>
            <Condition/>
            <ShoppingList/>
            <TaskList/>
            <ImageTwo/>
            <Stopwatch/>
            <ImageOne/>
            <AllPosts/>
            <Profile/>
            <About/>
            <TaskApp/>
        </div>
    );

}

function AllPosts() {
    return (
        <Section>
            <RecentPosts title={'里斯本的味道'} body={"...那些蛋挞！"}/>
        </Section>
    );
}

function RecentPosts({title, body}) {
    return (
        <Section>
            <Heading>最近的帖子</Heading>
            <Post
                title={title}
                body={body}
            />
            <Post
                title="探戈节奏中的布宜诺斯艾利斯"
                body="我爱它！"
            />
        </Section>
    );
}

function Post({title, body}) {
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
