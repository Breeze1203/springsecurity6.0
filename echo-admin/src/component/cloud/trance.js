import React, { useState, useEffect } from 'react';
import {
    Container,
    TextField,
    Table,
    TableBody,
    TableCell,
    TableContainer,
    TableHead,
    TableRow,
    Paper,
    Typography,
    Box,
    CircularProgress,
    TablePagination,
    Tooltip,
    Tabs,
    Tab,
} from '@mui/material';
import { styled } from '@mui/material/styles';
import { getTrance } from "../../axios/api/trance";

const StyledTableCell = styled(TableCell)(({ theme }) => ({
    '&.MuiTableCell-head': {
        backgroundColor: '#ffffff',
        color: theme.palette.grey[900],
        fontWeight: 'bold',
        whiteSpace: 'nowrap',
        padding: '12px 16px',
        borderBottom: `2px solid ${theme.palette.grey[400]}`,
        cursor: 'pointer',
    },
    '&.MuiTableCell-body': {
        padding: '10px 16px',
        borderBottom: `1px solid ${theme.palette.grey[300]}`,
        whiteSpace: 'nowrap',
    },
}));

const StyledTableRow = styled(TableRow)(({ theme }) => ({
    '&:hover': {
        backgroundColor: theme.palette.grey[100],
    },
}));

// Tab 面板组件
const TabPanel = (props) => {
    const { children, value, index, ...other } = props;
    return (
        <div
            role="tabpanel"
            hidden={value !== index}
            id={`tabpanel-${index}`}
            aria-labelledby={`tab-${index}`}
            {...other}
        >
            {value === index && <Box sx={{ p: 3 }}>{children}</Box>}
        </div>
    );
};

const TraceViewer = () => {
    const [traces, setTraces] = useState([]);
    const [loading, setLoading] = useState(false);
    const [filters, setFilters] = useState({
        serviceName: '',
        spanName: '',
        status: '',
    });
    const [page, setPage] = useState(0);
    const [rowsPerPage, setRowsPerPage] = useState(10);
    const [tabValue, setTabValue] = useState(0); // Tab 切换状态

    const fetchTraces = async (limit) => {
        setLoading(true);
        try {
            const response = await getTrance(limit);
            setTraces(response.data);
        } catch (error) {
            console.error('Error fetching traces:', error);
        } finally {
            setLoading(false);
        }
    };

    useEffect(() => {
        fetchTraces(10);
    }, []);

    const handleFilterChange = (e) => {
        const { name, value } = e.target;
        setFilters((prev) => ({ ...prev, [name]: value }));
    };

    const filteredTraces = traces.filter((traceArray) => {
        const span = traceArray[0];
        const serviceNameMatch = filters.serviceName
            ? span.localEndpoint?.serviceName?.toLowerCase().includes(filters.serviceName.toLowerCase())
            : true;
        const spanNameMatch = filters.spanName
            ? span.name?.toLowerCase().includes(filters.spanName.toLowerCase())
            : true;
        const statusMatch = filters.status
            ? span.tags?.status === filters.status
            : true;
        return serviceNameMatch && spanNameMatch && statusMatch;
    });

    const paginatedTraces = filteredTraces.slice(
        page * rowsPerPage,
        page * rowsPerPage + rowsPerPage
    );

    const handleChangeRowsPerPage = (event) => {
        setRowsPerPage(parseInt(event.target.value, 10));
        setPage(0);
        fetchTraces(parseInt(event.target.value));
    };

    const handleTabChange = (event, newValue) => {
        setTabValue(newValue);
    };

    // 表头定义
    const headers = [
        'trace id',
        'span id',
        'kind',
        'method name',
        'timestamp',
        'duration (ms)',
        'service name',
        'ipv4',
        'tags.client.name',
        'tags.exception',
        'tags.http.url',
        'tags.method',
        'tags.outcome',
        'tags.status',
        'tags.uri',
    ];

    // 表头提示
    const headerTooltips = {
        'trace id': '追踪ID，唯一标识一次完整请求',
        'span id': 'Span ID，标识请求中的一个操作',
        'kind': '操作类型，例如客户端（CLIENT）或服务端（SERVER）',
        'method name': '方法名称，例如 HTTP 请求类型和路径',
        'timestamp': '操作开始时间',
        'duration (ms)': '操作耗时（毫秒）',
        'service name': '服务名称，标识执行操作的服务',
        'ipv4': '服务所在的 IP 地址',
        'tags.client.name': '客户端名称，例如请求的目标服务',
        'tags.exception': '异常信息，若无则为 none',
        'tags.http.url': 'HTTP 请求的完整 URL',
        'tags.method': 'HTTP 请求方法，例如 GET、POST',
        'tags.outcome': '操作结果，例如成功（SUCCESS）或失败',
        'tags.status': 'HTTP 状态码，例如 200、204',
        'tags.uri': '请求的 URI，若无则为 none',
    };

    return (
        <Container maxWidth="xl" sx={{ mt: 4, mb: 4 }}>
            {/* Tab 切换 */}
            <Tabs value={tabValue} onChange={handleTabChange} centered sx={{ mb: 3 }}>
                <Tab label="追踪表格" />
                <Tab label="Zipkin 视图" />
            </Tabs>

            {/* Tab 面板 0：追踪表格 */}
            <TabPanel value={tabValue} index={0}>
                <Box sx={{ display: 'flex', gap: 2, mb: 3 }}>
                    <TextField
                        label="服务名称"
                        name="serviceName"
                        value={filters.serviceName}
                        onChange={handleFilterChange}
                        variant="outlined"
                        size="small"
                        fullWidth
                        sx={{ bgcolor: '#fff', borderRadius: 1 }}
                    />
                    <TextField
                        label="方法类型"
                        name="spanName"
                        value={filters.spanName}
                        onChange={handleFilterChange}
                        variant="outlined"
                        size="small"
                        fullWidth
                        sx={{ bgcolor: '#fff', borderRadius: 1 }}
                    />
                    <TextField
                        label="状态码"
                        name="status"
                        value={filters.status}
                        onChange={handleFilterChange}
                        variant="outlined"
                        size="small"
                        fullWidth
                        sx={{ bgcolor: '#fff', borderRadius: 1 }}
                    />
                </Box>

                {loading && (
                    <Box sx={{ display: 'flex', justifyContent: 'center', my: 4 }}>
                        <CircularProgress />
                    </Box>
                )}

                {!loading && (
                    <>
                        <TableContainer component={Paper} sx={{ boxShadow: 3, borderRadius: 2 }}>
                            <Table sx={{ minWidth: 1600 }}>
                                <TableHead>
                                    <TableRow>
                                        {headers.map((header) => (
                                            <Tooltip key={header} title={headerTooltips[header] || ''} arrow>
                                                <StyledTableCell>{header}</StyledTableCell>
                                            </Tooltip>
                                        ))}
                                    </TableRow>
                                </TableHead>
                                <TableBody>
                                    {paginatedTraces.length === 0 && (
                                        <StyledTableRow>
                                            <StyledTableCell colSpan={15} align="center">
                                                no traces found
                                            </StyledTableCell>
                                        </StyledTableRow>
                                    )}
                                    {paginatedTraces.map((traceArray) => {
                                        const span = traceArray[0];
                                        return (
                                            <StyledTableRow key={span.id}>
                                                <StyledTableCell>{span.traceId}</StyledTableCell>
                                                <StyledTableCell>{span.id}</StyledTableCell>
                                                <StyledTableCell>{span.kind || '-'}</StyledTableCell>
                                                <StyledTableCell>{span.name || 'unnamed'}</StyledTableCell>
                                                <StyledTableCell>
                                                    {new Date(span.timestamp / 1000).toLocaleString()}
                                                </StyledTableCell>
                                                <StyledTableCell>
                                                    {span.duration ? (span.duration / 1000).toFixed(2) : '-'}
                                                </StyledTableCell>
                                                <StyledTableCell>
                                                    {span.localEndpoint?.serviceName || 'unknown'}
                                                </StyledTableCell>
                                                <StyledTableCell>{span.localEndpoint?.ipv4 || '-'}</StyledTableCell>
                                                <StyledTableCell>{span.tags?.['client.name'] || '-'}</StyledTableCell>
                                                <StyledTableCell>{span.tags?.exception || '-'}</StyledTableCell>
                                                <StyledTableCell>{span.tags?.['http.url'] || '-'}</StyledTableCell>
                                                <StyledTableCell>{span.tags?.method || '-'}</StyledTableCell>
                                                <StyledTableCell>{span.tags?.outcome || '-'}</StyledTableCell>
                                                <StyledTableCell>{span.tags?.status || '-'}</StyledTableCell>
                                                <StyledTableCell>{span.tags?.uri || '-'}</StyledTableCell>
                                            </StyledTableRow>
                                        );
                                    })}
                                </TableBody>
                            </Table>
                        </TableContainer>
                        <TablePagination
                            rowsPerPageOptions={[10, 20, 50]}
                            component="div"
                            count={filteredTraces.length}
                            rowsPerPage={rowsPerPage}
                            page={page}
                            onRowsPerPageChange={handleChangeRowsPerPage}
                            sx={{ mt: 2 }}
                        />
                    </>
                )}
            </TabPanel>

            {/* Tab 面板 1：Zipkin iframe */}
            <TabPanel value={tabValue} index={1}>
                <Paper
                    elevation={3}
                    sx={{
                        height: '70vh', // 固定高度，可调整
                        display: 'flex',
                        overflow: 'hidden',
                    }}
                >
                    <iframe
                        src="http://localhost:9411/zipkin/"
                        title="Zipkin UI"
                        style={{
                            border: 'none',
                            width: '100%',
                            height: '100%',
                        }}
                        allowFullScreen
                    />
                </Paper>
            </TabPanel>
        </Container>
    );
};

export default TraceViewer;
