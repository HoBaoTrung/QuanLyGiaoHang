import { useEffect, useState } from "react";
import Apis, { endpoint } from "../configs/Apis";
import MySpinner from "../layout/Spinner";
import { Form } from "react-bootstrap";
import '../static/css/CreateOrder.css';
const FormSelectProvince = ({ locationName, selectedProvince, disabled }) => {
    const [provinces, setProvince] = useState(null)
    const loadProvinces = async () => {

        try {

            const res = await Apis.get(endpoint['get-provinces'](locationName));
            const provincesData = res.data;
            setProvince(provincesData)

        }
        catch (ex) { console.error(ex) }


    };
    useEffect(() => {
        loadProvinces()
    }, [])

    return (<>
        <label>Tỉnh/Thành phố:</label>
        <Form.Select

            disabled={disabled && locationName}
            onChange={(e) => selectedProvince(e.target.value)} className="custom-select" aria-label="Default select example"
        >

            <option>Chọn Tỉnh/Thành phố</option>
            {provinces ? <>
                {provinces.map(p =>
                    <option key={p.id} value={p.id}>{p.provinceName}</option>
                )}
            </> : <MySpinner />

            }

        </Form.Select>
    </>);
}
export default FormSelectProvince