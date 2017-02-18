const el = '#memo-app'

const data = {
  memos: ['foo', 'bar', 'baz'],
  source: '',
  current: {},
  saving: false,
  notified: false
}

const computed = {
  built () {
    const source = this.source || ''
    return marked(source)
  }
}

const filters = {
  title (value) {
    const content = value || ''
    const title = marked.lexer(content)
      .filter(token => token.type == 'heading')
      .map(token => token.text)[0]
    return title || '<No title>'
  }
}

const methods = {
  edit (memo) {
    const current = memo || {}
    this.current = current
    this.source = current.content
  },
  isActive (memo) {
    return this.current.id == memo.id
  },
  save () {
    this.saving = true
    const saved = () => {
      this.saving = false
      this.notified = true
      setTimeout(() => this.closeNotification(), 3000)
    }
    const proc = () => {
      const memo = this.current
      const body = new URLSearchParams()
      body.append('content', this.source)
      const init = { method: 'POST', body }
      if (memo.id) {
        const req = new Request('memos/' + memo.id, init)
        return fetch(req).then(resp => {
          memo.content = this.source
        })
      }
      const req = new Request('memos', init)
      return fetch(req).then(resp => resp.json()).then(json => {
        this.memos.unshift(json)
      })
    }
    proc().then(saved).catch(saved)
  },
  closeNotification () {
    this.notified = false
  }
}

const created = function() {
  const req = new Request('memos')
  fetch(req).then(resp => resp.json()).then(json => {
    this.memos = json
  })
}

new Vue({
  el,
  data,
  computed,
  filters,
  methods,
  created
})

