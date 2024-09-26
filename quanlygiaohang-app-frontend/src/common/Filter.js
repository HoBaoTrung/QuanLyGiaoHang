import { useEffect, useState } from "react";
import { Button, Form } from "react-bootstrap";
import { FaSearch } from 'react-icons/fa';
import Apis, { authApi, endpoint } from "../configs/Apis"; 
import MySpinner from "../layout/Spinner";
import '../static/css/Slider.css';


const Filter = ({ onSearch }) => {
    const [service, setService] = useState([]);
    const [fromProvinces, setFromProvinces] = useState([]);
    const [toProvinces, setToProvinces] = useState([]);

    const [selectedFromProvince, setSelectedFromProvince] = useState("");
    const [selectedToProvince, setSelectedToProvince] = useState("");
    const [selectedServices, setSelectedServices] = useState([]);

    const loadData = async () => {
        try {
            let form=new FormData
            form.append("getActiveService", true)
            let res = await authApi().post(endpoint['get-all-service'],form);
            console.info(res.data)
            setService(res.data._embedded.serviceList || []);

            res = await Apis.get(endpoint['get-provinces'](undefined));
            setFromProvinces(res.data || []);
            setToProvinces(res.data || []);
        } catch (ex) {
            console.error(ex);
        }
    };

    const handleSearch = () => {
        const data = {
            from: selectedFromProvince,
            to: selectedToProvince,
            services: selectedServices
        };
        onSearch(data); // Truyền dữ liệu về component cha
        
    };

    useEffect(() => {
        loadData();
    }, []);



    return (
        <div style={{ position: "relative", border: "solid 1px black", width: "60%" }} className="m-3 p-3 d-flex">
            <Form>
                <Form.Select style={{ width: "300px", margin: "auto", textAlign: "center" }} aria-label="From"
                onChange={(e) => setSelectedFromProvince(e.target.value)}>
                    <option value="">From</option>
                    {fromProvinces.length > 0 ? fromProvinces.map(p => (
                        <option key={p.id} value={p.id}>{p.provinceName}</option>
                    )) : <MySpinner />}
                </Form.Select>

                <Form.Select style={{ width: "300px", margin: "20px", textAlign: "center" }} aria-label="To"
                 onChange={(e) => setSelectedToProvince(e.target.value)}>
                    <option value="">To</option>
                    {toProvinces.length > 0 ? toProvinces.map(p => (
                        <option key={p.id} value={p.id}>{p.provinceName}</option>
                    )) : <MySpinner />}
                </Form.Select>

                <div className="d-flex">
                    <div className="mb-3">
                        {service.length > 0 ? service.map(s => (
                            <Form.Check
                                key={s.id} // Đảm bảo key duy nhất
                                type="checkbox"
                                id={s.name}
                                label={s.name}
                                onChange={(e) => {
                                    const isChecked = e.target.checked;
                                    setSelectedServices(prev => isChecked 
                                        ? [...prev, s.id] 
                                        : prev.filter(id => id !== s.id));
                                }}
                            />
                        )) : <MySpinner />}
                    </div>
          

                </div>

                <Button  onClick={handleSearch} variant="info" style={{ position: "absolute", bottom: "10px", right: "10px" }}>
                    <FaSearch style={{ marginRight: "5px" }} />
                    Tìm
                </Button>

            </Form>
        </div>
    );
};

export default Filter;
