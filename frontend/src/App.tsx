import { useState } from 'react'
import { checkHealth, type HealthResponse } from './api/health'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

export default function App() {
  const [healthStatus, setHealthStatus] = useState<HealthResponse | null>(null)
  const [loading, setLoading] = useState(false)
  const [error, setError] = useState<string | null>(null)

  const handleHealthCheck = async () => {
    setLoading(true)
    setError(null)
    try {
      const health = await checkHealth()
      setHealthStatus(health)
    } catch (err) {
      setError(err instanceof Error ? err.message : 'Health check failed')
    } finally {
      setLoading(false)
    }
  }

  return (
    <>
      <div>
        <a href="https://vite.dev" target="_blank">
          <img src={viteLogo} className="logo" alt="Vite logo" />
        </a>
        <a href="https://react.dev" target="_blank">
          <img src={reactLogo} className="logo react" alt="React logo" />
        </a>
      </div>
      <h1>Synder Algo7</h1>
      <div className="card">
        <button
          onClick={handleHealthCheck}
          disabled={loading}
          className="btn-primary"
        >
          {loading ? 'Checking...' : 'Check Backend Health'}
        </button>

        {error && (
          <div className="mt-4 p-3 rounded-md border" style={{ color: 'var(--danger)', borderColor: 'var(--danger)' }}>
            <strong>Error:</strong> {error}
          </div>
        )}

        {healthStatus && (
          <div className="mt-4 text-left">
            <strong>Health Status:</strong>
            <pre className="card-dark mt-2 overflow-auto">
              {JSON.stringify(healthStatus, null, 2)}
            </pre>
          </div>
        )}
      </div>
      <p className="read-the-docs">
        Backend API: http://10.86.116.174:8080
      </p>
    </>
  )
}