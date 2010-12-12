class CreateInvites < ActiveRecord::Migration
  def self.up
    create_table :invites do |t|
      t.references :group
      t.string :email, :null => false, :limit => 100
      t.string :token, :null => false, :limit => 16
      t.boolean :active, :null => false, :default => true
      t.timestamps
    end

    add_index :invites, :group_id
    add_index :invites, :email
    add_index :invites, :token
  end

  def self.down
    drop_table :invites
  end
end
