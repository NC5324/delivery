<template>
  <div style="padding: 5% 10%;">
    <label class="btn btn-default">
      <input type="file" multiple @change="selectFile" />
    </label>

    <button class="btn btn-success"
            :disabled="!selectedFiles"
            @click="uploadFiles"
    >
      Upload
    </button>
    <b-table style="text-align: left" stacked="lg" striped hover :items="fileInfos" :fields="fields">
      <template #cell(url)="imgRow">
        <b-img thumbnail fluid :src="imgRow.item.url" width="150rem" height="150rem" :alt="imgRow.item.name"></b-img>
      </template>
      <template #cell(actions)="row">
        <b-button
          v-if="$router.currentRoute.fullPath === '/manage/files'"
          style="width: 100%;"
          variant="danger"
          @click="deleteFileByName(row.item.name)">Изтрий</b-button>
        <b-button
          v-if="$router.currentRoute.fullPath === '/manage/products'"
          style="width: 100%;"
          variant="success"
          @click="emitRequest(row.item)">Избери</b-button>
      </template>
    </b-table>
  </div>
</template>

<script>
import FileService from '../services/files-service'
export default {
  name: 'ManageFiles',
  props: {
    myProp: { type: Function }
  },
  data () {
    return {
      selectedFiles: undefined,
      progressInfos: [],
      message: '',

      selectedPath: '',

      fileInfos: [],
      fields: [
        { key: 'name', label: 'Име' },
        { key: 'url', label: 'Снимка' },
        { key: 'size', label: 'Размер' },
        { key: 'actions', label: 'Действия' }
      ],
      filters: {}
    }
  },
  mounted () {
    this.getAllFiles()
  },
  methods: {
    getAllFiles () {
      FileService.getFiles().then(
        response => {
          this.fileInfos = response.data
        },
        error => {
          this.content =
            (error.response && error.response.data) ||
            error.message ||
            error.toString()
        }
      )
    },
    selectFile () {
      this.progressInfos = []
      this.selectedFiles = event.target.files
    },
    uploadFiles () {
      this.message = ''

      for (let i = 0; i < this.selectedFiles.length; i++) {
        this.upload(i, this.selectedFiles[i])
      }
    },
    upload (idx, file) {
      FileService.upload(file)
        .then(() => {
          return FileService.getFiles()
        })
        .then((response) => {
          this.fileInfos = response.data
        })
    },
    deleteFileByName (name) {
      FileService.deleteFileByName(name)
        .then(() => {
          return FileService.getFiles()
        })
        .then((response) => {
          this.fileInfos = response.data
        })
    },
    emitRequest (item) {
      this.changeSelected(item)
      this.$emit('nc-on-confirm')
    },
    changeSelected (item) {
      if (this.$router.currentRoute.fullPath === '/manage/products') {
        localStorage.setItem('selectedProductImage', JSON.stringify(item))
      }
    }
  }
}
</script>

<style scoped>

</style>
