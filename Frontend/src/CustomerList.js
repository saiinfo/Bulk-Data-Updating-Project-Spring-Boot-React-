import React, { useState, useEffect, useRef } from 'react';
import axios from 'axios';
import {
  Container,
  Typography,
  TableContainer,
  Paper,
  Table,
  TableHead,
  TableRow,
  TableCell,
  TableBody,
  Button,
  IconButton,
  Pagination,
  FormControl,
  Select,
  MenuItem,
  Grid,

} from '@mui/material';
import GetAppIcon from '@mui/icons-material/GetApp';
import CloudUploadIcon from '@mui/icons-material/CloudUpload';

const PAGE_SIZE = 5; 

function CustomerList() {

  const [totalPages, setTotalPages] = useState(0);
  const [currentPage, setCurrentPage] = useState(0);
  const fileInputRef = useRef(null);
  const [cities, setCities] = useState([]);

  const [customers, setCustomers] = useState([]);
 
  const [selectedCustomers, setSelectedCustomers] = useState([]);
  const [selectedCity, setSelectedCity] = useState('');
 const storedFile = JSON.parse(localStorage.getItem('selectedFile'));
 const [selectedFile, setSelectedFile] = useState(storedFile || null);
 const [isUploading, setIsUploading] = useState(false); // Add a loading state
 const [uploadSuccess, setUploadSuccess] = useState(false); // Add a success state
  const handleClick = () => {
    fileInputRef.current.click();
  };

  const handleFileChange = (event) => {
    const file = event.target.files[0];
    if (file) {
        setSelectedFile(file); // Store selected file in state
        handleUpload(file);
    }
  };


  

  const fetchCustomers = (page) => {
    axios.get(`/customers?page=${page}&size=${PAGE_SIZE}`)
      .then(response => {
        setCustomers(response.data.customers);
        setTotalPages(response.data.totalPages);
      })
      .catch(error => {
        console.error('Error fetching customers:', error);
      });
  };

  useEffect(() => {
    // Fetch cities and customers from backend
    axios.get('/api/cities').then(response => setCities(response.data));
    axios.get('/api/customers').then(response => setCustomers(response.data));
  }, []);

  useEffect(() => {
    // Store the selectedFile value in localStorage
    localStorage.setItem('selectedFile', JSON.stringify(selectedFile));

    fetchCustomers(currentPage);
  }, [currentPage, selectedFile]); // Add selectedFile as a dependency
 
  const handlePageChange = (event, newPage) => {
    setCurrentPage(newPage - 1); // Convert to zero-based index
  };


  const handleCustomerCheckboxChange = (customerId) => {
    if (selectedCustomers.includes(customerId)) {
      setSelectedCustomers(selectedCustomers.filter((id) => id !== customerId));
    } else {
      setSelectedCustomers([...selectedCustomers, customerId]);
    }
  };

  const handleExport = () => {
    axios.get('/export', { responseType: 'blob' })
      .then(response => {
        const url = window.URL.createObjectURL(new Blob([response.data]));
        const link = document.createElement('a');
        link.href = url;
        link.setAttribute('download', 'customer_data.xlsx');
        document.body.appendChild(link);
        link.click();
      })
      .catch(error => {
        console.error('Error exporting file:', error);
      });
  };


  const handleCityChange = event => {
    setSelectedCity(event.target.value);
  };

  const handleApplyButtonClick = () => {
    // Make a PUT request to update selected customers' city
    axios.put(`/api/bulk-update/${selectedCity}`, selectedCustomers.map(customerId => ({ customerId })))
      .then(response => {
        // Handle success, update UI or show a message
        setSelectedCustomers([]); // Clear selected customers after update
        setSelectedCity(''); // Clear selected city after update
        fetchCustomers(currentPage);
      })
      .catch(error => {
        // Handle error, show an error message
      });
  };
  const handleUpload = (selectedFile) => {
    setIsUploading(true); // Start uploading
    const formData = new FormData();
    formData.append('file', selectedFile);

    axios.post('/upload', formData)
      .then(response => {
        console.log(response.data);
        fetchCustomers(currentPage);
        setIsUploading(false); // Upload complete
        setUploadSuccess(true); // Set success state
      })
      .catch(error => {
        console.error('Error uploading file:', error);
        setIsUploading(false); // Upload complete (with error)
        setUploadSuccess(false); // Clear success state
      });

    fileInputRef.current.value = null;
    setSelectedFile(null);
  };
  // ... (existing handleCityChange, handleApplyButtonClick, handleUpload)
console.log(customers)
  return (
    <Container maxWidth="md">
      <Typography variant="h4" align="center" gutterBottom>
         Bulk Update
      </Typography>

     
      <Grid container spacing={2} justifyContent="flex-end" alignItems="center">
        <Grid item>
          <FormControl variant="outlined" style={{ minWidth: 200 }}>
          <Select
    value={selectedCity}
    onChange={handleCityChange}
    name="Choose City"
    displayEmpty  // This attribute ensures that the display starts empty
  >
    <MenuItem value="" disabled>
      Select a city
    </MenuItem>
    {cities.map((city) => (
      <MenuItem key={city.cityId} value={city.cityId}>
        {city.cityName}
      </MenuItem>
    ))}
  </Select>
          </FormControl>
        </Grid>
        <Grid item>
          <input
            type="file"
            ref={fileInputRef}
            style={{ display: 'none' }}
            onChange={handleFileChange}
            accept=".xlsx, .xls"
          />
          <Button
            variant="contained"
            color="primary"
            startIcon={<CloudUploadIcon />}
            onClick={() => fileInputRef.current.click()}
          >
            Upload
          </Button>
          {isUploading ? (
            <Typography variant="body1">Uploading...</Typography>
          ) : (
            <IconButton color="primary" onClick={handleExport}>
              <GetAppIcon />
            </IconButton>
          )}
        </Grid>
      </Grid>
           
      <div style={{ height: '30px'}}></div>  
      <TableContainer component={Paper}>
        <Table>
          <TableHead>
            <TableRow>
              <TableCell></TableCell> {/* Checkbox column */}
              <TableCell>ID</TableCell>
              <TableCell>First Name</TableCell>
              <TableCell>Last Name</TableCell>
              <TableCell>Phone</TableCell>
              <TableCell>City</TableCell>
              <TableCell>Acquired Date</TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {customers.map((customer) => (
              <TableRow key={customer.customerId}>
              <TableCell>
                <input
                  type="checkbox"
                  checked={selectedCustomers.includes(customer.customerId)}
                  onChange={() => handleCustomerCheckboxChange(customer.customerId)}
                />
              </TableCell>
              <TableCell>{customer.customerId}</TableCell>
              <TableCell>{customer.firstName}</TableCell>
              <TableCell>{customer.lastName}</TableCell>
              <TableCell>{customer.phone}</TableCell>
              <TableCell>
                {customer.city ? customer.city.cityName : 'N/A'} {/* Display city name or "N/A" */}
              </TableCell>
              <TableCell>{customer.acquiredDate}</TableCell>
            </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
      <div style={{ display: 'flex', justifyContent: 'center', marginTop: '20px' }}>
        <Pagination
          count={totalPages}
          page={currentPage + 1}
          onChange={handlePageChange}
          shape="rounded"
        />
      </div>
      </div>
      <div style={{ height: '30px'}}></div>  
      <Button
  variant="contained"
  color="primary"
  onClick={handleApplyButtonClick}
  disabled={!selectedCity || selectedCustomers.length === 0}
>
  Apply
</Button>

    </Container>
  );
}

export default CustomerList;
