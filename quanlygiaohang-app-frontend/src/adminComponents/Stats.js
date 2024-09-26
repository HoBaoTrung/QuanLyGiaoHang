import { useEffect, useState } from "react";
import ColumnChart from "./ColumnChart";
import { ButtonGroup, ToggleButton } from "react-bootstrap";
import PieChart from "./PieChart";

const Stats = () => {
    const [year, setYear] = useState(new Date().getFullYear());
    const [radioValue, setRadioValue] = useState('month');
    const radios = [
        { name: 'month', value: '1' },
        { name: 'quarter', value: '2' }
    ];



    return (
        <>
            <div style={{ overflow: "auto", height: "100%" }}>
                
                <input
                    className="mb-3"
                    type="number"
                    max={year}
                    value={year}
                    onChange={(e) => setYear(e.target.value)}
                    placeholder="Enter year"
                />
         
                <br />
                <ButtonGroup>
                    {radios.map((radio, idx) => (
                        <ToggleButton
                            key={idx}
                            id={`radio-${idx}`}
                            type="radio"
                            variant={idx % 2 ? 'outline-success' : 'outline-danger'}
                            name="radio"
                            value={radio.name}
                            checked={radioValue === radio.name}
                            onChange={(e) => setRadioValue(e.currentTarget.value)}
                            className="mr-2 mb-3"
                        >
                            {radio.name}
                        </ToggleButton>
                    ))}
                </ButtonGroup>
                <ColumnChart year={year} period={radioValue}  kind={"price"}/>
                <br/>
                <ColumnChart year={year} period={radioValue} kind={"quantity"}/>
                <PieChart year={year} />
            </div>

        </>
    );
}
export default Stats