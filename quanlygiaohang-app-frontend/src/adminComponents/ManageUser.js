import React, { useEffect, useState } from 'react';
import '../static/css/AdminDashboard.css';
import { Button, Image, Spinner, Table } from 'react-bootstrap';
import { authApi, endpoint } from '../configs/Apis';
import Pagination from '../layout/Pagination';
import { useNavigate } from 'react-router-dom';


const ManagerUser = () => {
    const [listUser, setListUser] = useState(null)
    const nav=useNavigate()
    const [currentPage, setCurrentPage] = useState(1); // Trang hiện tại
    const [totalPages, setTotalPages] = useState(2); // Tổng số trang
    const loadUser = async (page) => {
        try {
           
            let url = `${endpoint['get-admin-user']}?page=${page - 1}`

            const res = await authApi().get(url)
            console.info(res)
            setTotalPages(res.data.totalPages)
            setListUser(res.data.content)

        }
        catch (ex) { console.log(ex) }
        finally { }
    }

    useEffect(() => {
        loadUser(currentPage);

    }, [currentPage]);
    const handlePageChange = (page) => {
        if (page < 1 || page > totalPages) return;
        setCurrentPage(page);

    };
    if (listUser === null)
        return <Spinner animation="border" />;

    return (
        <>

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
                        <th>Role</th>
                        <th>Email</th>
                        <th>Phone</th>
                        <th>Avatar</th>
                        <th></th>
                    </tr>
                </thead>
                <tbody>
                    {listUser.map(u =>
                        <tr>
                            <td>{u.id}</td>
                            <td>{u.username}</td>
                            <td>
                                {u.roles.map(
                                    r => r.name
                                ).join(', ')}
                            </td>
                            <td>{u.email}</td>
                            <td>{u.phone}</td>
                            <td>
                                <Image height={'50px'} src={u.avatar} />
                            </td>
                            <td>
                                <Button variant='info' onClick={() => nav(`/admin/customersDetail/${u.id}`)}>Xem</Button>
                            </td>
                        </tr>)}


                </tbody>
            </Table>
        </>
    );
};

export default ManagerUser;
