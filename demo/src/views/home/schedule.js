import React, { useState } from 'react';
import { Calendar } from 'element-react';
import 'element-react/dist/theme-chalk/index.css';

const SystemSysSchedule = () => {
    const [value, setValue] = useState(new Date());

    return (
        <div className="system-sys-schedule">
            <Calendar
                modelValue={value}
                onCalendarChange={setValue}
            />
        </div>
    );
};

export default SystemSysSchedule;
