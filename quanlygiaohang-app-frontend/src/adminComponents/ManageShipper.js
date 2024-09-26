import React, { useEffect, useState } from 'react';
import '../static/css/AdminDashboard.css';
import { Button, Image, Spinner, Table } from 'react-bootstrap';
import { authApi, endpoint } from '../configs/Apis';
import Pagination from '../layout/Pagination';
import { useNavigate } from 'react-router-dom';


const ManagerShipper = () => {
    const [listUser, setListUser] = useState(null)
    const [active, setActive] = useState(true)
    const nav = useNavigate();
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [totalPages, setTotalPages] = useState(2); // Tổng số trang
    const loadUser = async (page) => {
        try {
            let url = `${endpoint['get-admin-shipper']}?page=${page - 1}&active=${active}`

            const res = await authApi().get(url)

            setTotalPages(res.data.page.totalPages)

            setListUser(res.data._embedded.shipperList)


        }
        catch (ex) { console.log(ex) }
        finally { }
    }

    useEffect(() => {
        loadUser(currentPage);

    }, [currentPage, active]);
    const handlePageChange = (page) => {
        if (page < 1 || page > totalPages) return;
        setCurrentPage(page);

    };
    if (listUser === null)
        return <Spinner animation="border" />;

    return (
        <>
            <Button onClick={() => setActive(true)} variant='success'>Đã xác nhận</Button>
            <Button onClick={() => setActive(false)} variant="danger" className='m-3'>Chưa xác nhận</Button>

            <h3>Shippers List ({active ? 'Active' : 'Inactive'})</h3>
            <Pagination
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
            />


            <Table striped bordered hover  >
                <thead>
                    <tr>
                        <th>Id</th>
                        <th>Username</th>
                        <th>CMND/CCCD</th>
                        <th>Phone</th>
                        <th>Active</th>
                        <th>Avatar</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {listUser.map(u =>
                        <tr>
                            <td>{u.id}</td>
                            <td>{u.user.username}</td>
                            <td> {u.cmnd} </td>
                            <td>{u.user.phone}</td>
                            <td>{u.active ? 'Đã kích hoạt' : "Chưa kích hoạt"}</td>

                            <td>
                                <Image height={'50px'} src={u.user.avatar} />
                            </td>
                            <td>

                                <Button variant='info' className='mr-2'
                                    onClick={() => nav(`/admin/adminShippersDetail/${u.id}`)}
                                >Chi tiết</Button>

                                {u.user.roles.some(item => item.name === 'ADMIN') ? <></> :
                                    <Button variant='danger'>Xóa</Button>}
                            </td>
                        </tr>)}


                </tbody>
            </Table>
        </>
    );
};

export default ManagerShipper;
