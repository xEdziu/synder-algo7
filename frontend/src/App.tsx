import { useState } from 'react'
import { checkHealth, type HealthResponse } from './api/health'
import reactLogo from './assets/react.svg'
import viteLogo from '/vite.svg'
import './App.css'

function App() {
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
        <button onClick={handleHealthCheck} disabled={loading}>
          {loading ? 'Checking...' : 'Check Backend Health'}
        </button>

        {error && (
          <div style={{ color: 'red', marginTop: '1rem' }}>
            <strong>Error:</strong> {error}
          </div>
        )}

        {healthStatus && (
          <div style={{ marginTop: '1rem', textAlign: 'left' }}>
            <strong>Health Status:</strong>
            <pre style={{ background: '#1a1a1a', padding: '1rem', borderRadius: '8px' }}>
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

      <div className="grid sm:grid-cols-2 md:grid-cols-3 lg:grid-cols-4 gap-4">
        {colors.map(({ name, value }) => (
          <div
            key={name}
            className="flex flex-col border border-[var(--border-muted)] rounded-lg overflow-hidden shadow-sm"
          >
            <div
              className="h-20 w-full"
              style={{ backgroundColor: `var(${name})` }}
            ></div>
            <div className="p-3 text-sm">
              <div className="font-semibold text-[var(--color-tertiary)]">
                {name}
              </div>
              <div className="text-[var(--text-muted)]">{value}</div>
            </div>
          </div>
        ))}
      </div>
    </div>
  );
}
