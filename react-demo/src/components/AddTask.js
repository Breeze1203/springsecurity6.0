import { useState } from 'react';

export default function AddTask({onAddTask}) {
    const [text, setText] = useState('');
    return (
        <>
            <input
                placeholder="添加任务"
                value={text}
                onChange={(e) => setText(e.target.value)}
            />
            <button
                onClick={() => {
                    onAddTask(text);
                    setText('')
                }}>
                添加
            </button>
        </>
    );
}
