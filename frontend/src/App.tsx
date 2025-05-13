// src/App.tsx
import React, { useState, useEffect } from 'react';
import { Container, Typography, Box, AppBar, Toolbar, Chip } from '@mui/material';
import SmsForm from './components/SmsForm';
import SmsResults from './components/SmsResults';
import { smsApi } from './services/api';
import type { SmsResponse } from './types/api.types';
import './App.css';

type ApiStatus = 'checking' | 'connected' | 'error';

function App() {
  const [loading, setLoading] = useState<boolean>(false);
  const [result, setResult] = useState<SmsResponse | null>(null);
  const [error, setError] = useState<string | null>(null);
  const [apiStatus, setApiStatus] = useState<ApiStatus>('checking');

  // Check API health on mount
  useEffect(() => {
    checkApiHealth();
  }, []);

  const checkApiHealth = async (): Promise<void> => {
    try {
      const health = await smsApi.checkHealth();
      setApiStatus(health.status === 'UP' ? 'connected' : 'error');
    } catch (err) {
      setApiStatus('error');
    }
  };

  const handleSubmit = async (message: string, phoneNumber: string): Promise<void> => {
    setLoading(true);
    setError(null);
    setResult(null);

    try {
      const response = await smsApi.sendSms({ message, phoneNumber });
      setResult(response);
    } catch (err) {
      const errorMessage = err instanceof Error ? err.message : 'Failed to send SMS';
      setError(errorMessage);
    } finally {
      setLoading(false);
    }
  };

  const getApiStatusColor = (status: ApiStatus): 'success' | 'error' | 'warning' => {
    switch (status) {
      case 'connected':
        return 'success';
      case 'error':
        return 'error';
      default:
        return 'warning';
    }
  };

  return (
    <Box sx={{ flexGrow: 1 }}>
      <AppBar position="static">
        <Toolbar>
          <Typography variant="h6" component="div" sx={{ flexGrow: 1, color: 'white' }}>
            SMS Splitter
          </Typography>
          <Chip
            label={`API: ${apiStatus}`}
            color={getApiStatusColor(apiStatus)}
            size="small"
          />
        </Toolbar>
      </AppBar>

      <Container maxWidth="lg" sx={{ mt: 4 }}>
        <Typography variant="h4" align="center" gutterBottom sx={{ color: 'white' }}>
          SMS Message Splitter
        </Typography>
        <Typography variant="body1" align="center" sx={{ mb: 4, color: 'white' }}>
          Send long messages that will be automatically split into 160-character SMS parts
        </Typography>

        <SmsForm onSubmit={handleSubmit} loading={loading} />
        <SmsResults result={result} error={error} />
      </Container>
    </Box>
  );
}

export default App;